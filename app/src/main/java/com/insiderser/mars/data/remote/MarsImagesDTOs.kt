package com.insiderser.mars.data.remote

import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.SolarDaysSinceLanding

data class NasaMarsImages(
    val photos: List<NasaMarsImage> = emptyList()
)

data class NasaMarsImage(
    val id: Int,
    val img_src: String,
)

fun NasaMarsImage.toMarsImage(sol: SolarDaysSinceLanding): MarsImage = MarsImage(id, url = img_src, sol)
