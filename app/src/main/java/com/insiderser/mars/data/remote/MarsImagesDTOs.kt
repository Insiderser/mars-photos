package com.insiderser.mars.data.remote

import com.insiderser.mars.model.MarsImage

data class NasaMarsImages(
    val photos: List<NasaMarsImage> = emptyList()
)

data class NasaMarsImage(
    val id: Int,
    val img_src: String,
)

fun NasaMarsImage.toMarsImage(): MarsImage = MarsImage(id, url = img_src)
