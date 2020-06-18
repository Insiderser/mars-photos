package com.insiderser.mars.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.insiderser.mars.data.db.MarsImagesDao
import com.insiderser.mars.model.MarsImage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject

interface MarsImagesRepository {

    fun getImages(): LiveData<PagedList<MarsImage>>
}

class DefaultMarsImagesRepository @Inject constructor(
    private val imagesDao: MarsImagesDao,
    private val imagesLoader: MarsImagesLoader,
) : MarsImagesRepository {

    override fun getImages(): LiveData<PagedList<MarsImage>> {
        val imagesFactory = imagesDao.getImages()
        return imagesFactory.toLiveData(
            pageSize = PAGE_SIZE,
            boundaryCallback = imagesLoader
        )
    }
}

private const val PAGE_SIZE = 25

@Module
@InstallIn(ApplicationComponent::class)
@Suppress("unused")
interface MarsImagesRepositoryModule {

    @Binds
    fun bindMarsImagesRepository(impl: DefaultMarsImagesRepository): MarsImagesRepository
}
