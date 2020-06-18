package com.insiderser.mars.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaImagesWebService {

    @GET(NasaRemoteConfig.MARS_IMAGES_URL)
    fun getImages(
        @Query("sol") solarDay: Int
    ): Single<NasaMarsImages>
}

@Module
@InstallIn(ApplicationComponent::class)
object NasaImagesWebServiceModule {

    @Provides
    fun provideNasaMarsImagesWebService(): NasaImagesWebService {
        val moshiConverterFactory = MoshiConverterFactory.create()
        val rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        val retrofit = Retrofit.Builder()
            .baseUrl(NasaRemoteConfig.NASA_BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()

        return retrofit.create(NasaImagesWebService::class.java)
    }
}
