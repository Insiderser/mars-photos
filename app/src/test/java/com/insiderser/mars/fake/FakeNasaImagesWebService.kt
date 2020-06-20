package com.insiderser.mars.fake

import com.insiderser.mars.data.remote.NasaImagesWebService
import com.insiderser.mars.data.remote.NasaMarsImages
import io.reactivex.Single

class FakeNasaImagesWebService : NasaImagesWebService {

    lateinit var images: Single<NasaMarsImages>

    override fun getImages(solarDay: Int): Single<NasaMarsImages> = images
}
