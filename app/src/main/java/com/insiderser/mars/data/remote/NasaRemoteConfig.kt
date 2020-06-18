package com.insiderser.mars.data.remote

import com.insiderser.mars.BuildConfig

object NasaRemoteConfig {

    const val ROVER = "curiosity"
    const val CAMERA = "MAST" // MAST camera provides colorful panoramic images.

    const val API_KEY = BuildConfig.NASA_API_KEY

    const val NASA_BASE_URL = "https://api.nasa.gov/"
    const val MARS_IMAGES_URL = "/mars-photos/api/v1/rovers/$ROVER/photos?camera=$CAMERA&api_key=$API_KEY"
}
