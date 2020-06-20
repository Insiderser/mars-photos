package com.insiderser.mars.data.remote

import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.Sol

data class NasaMarsImages(
    val photos: List<NasaMarsImage>
)

data class NasaMarsImage(
    val id: Int,
    val img_src: String,
)

fun NasaMarsImage.toMarsImage(sol: Sol): MarsImage = MarsImage(id, url = img_src, sol)
