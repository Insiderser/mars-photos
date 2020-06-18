package com.insiderser.mars.data.remote

import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.SolarDaysSinceLanding
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Single
import javax.inject.Inject

interface RemoteMarsImagesDataSource {

    fun getImages(sol: SolarDaysSinceLanding): Single<List<MarsImage>>
}

class DefaultRemoteMarsImagesDataSource @Inject constructor(
    private val service: NasaImagesWebService,
) : RemoteMarsImagesDataSource {

    override fun getImages(sol: SolarDaysSinceLanding): Single<List<MarsImage>> = service.getImages(sol.value)
        .map { (photos) ->  // Using Kotlin's destructuring.
            photos.map { it.toMarsImage() }
        }
}

@Module
@InstallIn(ApplicationComponent::class)
interface RemoteMarsImagesDataSourceModule {

    @Binds
    @Suppress("unused")
    fun bindRemoteMarsImagesDataSource(default: DefaultRemoteMarsImagesDataSource): RemoteMarsImagesDataSource
}
