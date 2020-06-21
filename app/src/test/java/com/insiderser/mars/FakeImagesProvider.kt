package com.insiderser.mars

import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.Sol
import com.insiderser.mars.model.plus

object FakeImagesProvider {

    val image1 = MarsImage(4, "some_url", Sol.LANDING)
    val image2 = MarsImage(7, "another_url", Sol.LANDING.plus(100))
    val image3 = MarsImage(10000, "completely different URL", Sol.now())

    val list = listOf(image1, image2, image3)
}
