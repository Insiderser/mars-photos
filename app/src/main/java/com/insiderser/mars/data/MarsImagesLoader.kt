package com.insiderser.mars.data

import android.annotation.SuppressLint
import androidx.paging.PagedList
import com.insiderser.mars.data.db.MarsImagesDao
import com.insiderser.mars.data.remote.RemoteMarsImagesDataSource
import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.SolarDaysSinceLanding
import com.insiderser.mars.model.plus
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarsImagesLoader @Inject constructor(
    private val remoteDataSource: RemoteMarsImagesDataSource,
    private val imagesDao: MarsImagesDao,
    private val preferencesStorage: AppPreferencesStorage
) : PagedList.BoundaryCallback<MarsImage>() {

    private var isInProgress = false

    override fun onZeroItemsLoaded() {
        fetchNewImages()
    }

    override fun onItemAtEndLoaded(itemAtEnd: MarsImage) {
        fetchNewImages()
    }

    @SuppressLint("CheckResult")
    private fun fetchNewImages() {
        if (isInProgress) return
        isInProgress = true

        val nextPage = preferencesStorage.lastLoadedSolarDay?.plus(1) ?: SolarDaysSinceLanding.START

        Timber.d("Trying to fetch for solar day %s", nextPage)

        remoteDataSource.getImages(nextPage)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ nextImages ->
                Timber.d("Fetched successfully. Received %d images", nextImages.size)

                imagesDao.insertAll(nextImages)
                preferencesStorage.lastLoadedSolarDay = nextPage
                isInProgress = false

                if (nextImages.isEmpty()) {
                    fetchNewImages()
                }
            }, { error ->
                // TODO: display something to the user.
                Timber.e(error)
                isInProgress = false
            })
    }
}
