package com.insiderser.mars.data.remote

import com.insiderser.mars.fake.FakeNasaImagesWebService
import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.Sol
import io.reactivex.Single
import org.junit.Test
import java.io.IOException

class DefaultRemoteMarsImagesDataSourceTest {

    private val webService = FakeNasaImagesWebService()
    private val dataSource = DefaultRemoteMarsImagesDataSource(webService)

    @Test
    fun `getImages correctly handles list`() {
        val sol = Sol.landing

        val images = listOf(
            NasaMarsImage(4, "image url", sol.value),
            NasaMarsImage(100000000, "image url #2", sol.value),
        )
        webService.images = Single.just(NasaMarsImages(images))

        dataSource.getImages(sol)
            .test()
            .assertResult(
                listOf(
                    MarsImage(4, "image url", sol),
                    MarsImage(100000000, "image url #2", sol),
                )
            )
    }

    @Test
    fun `getImages correctly handles errors`() {
        webService.images = Single.error(IOException("My exception"))

        dataSource.getImages(Sol.landing)
            .test()
            .assertFailure(IOException::class.java)
    }

    @Test
    fun `getImages correctly handles empty list`() {
        webService.images = Single.just(NasaMarsImages(emptyList()))

        dataSource.getImages(Sol.landing)
            .test()
            .assertResult(emptyList())
    }
}
