package com.insiderser.mars.data

import android.annotation.SuppressLint
import androidx.paging.PagedList
import com.insiderser.mars.data.db.MarsImagesDao
import com.insiderser.mars.data.remote.NasaRemoteConfig
import com.insiderser.mars.data.remote.RemoteMarsImagesDataSource
import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.Sol
import com.insiderser.mars.model.plus
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarsImagesLoader @Inject constructor(
    private val remoteDataSource: RemoteMarsImagesDataSource,
    private val imagesDao: MarsImagesDao,
    private val preferencesStorage: AppPreferencesStorage
) : PagedList.BoundaryCallback<MarsImage>() {

    private var isInProgress = AtomicBoolean(false)

    override fun onZeroItemsLoaded() {
        fetchNewImages()
    }

    override fun onItemAtEndLoaded(itemAtEnd: MarsImage) {
        fetchNewImages()
    }

    @SuppressLint("CheckResult")
    private fun fetchNewImages() {
        if (isInProgress.getAndSet(true)) return

        val nextPage = preferencesStorage.lastLoadedSolarDay?.plus(1) ?: Sol.landing

        if (!NasaRemoteConfig.canFetch(nextPage)) {
            Timber.d("Cannot fetch %s", nextPage)
            isInProgress.set(false)
            return
        }

        Timber.d("Trying to fetch %s", nextPage)

        remoteDataSource.getImages(nextPage)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ nextImages ->
                Timber.d("Fetched successfully. Received %d images", nextImages.size)

                imagesDao.insertAll(nextImages)
                preferencesStorage.lastLoadedSolarDay = nextPage
                isInProgress.set(false)

                if (nextImages.isEmpty()) {
                    fetchNewImages()
                }
            }, { error ->
                // TODO: display something to the user.
                Timber.e(error)
                isInProgress.set(false)
            })
    }
}
