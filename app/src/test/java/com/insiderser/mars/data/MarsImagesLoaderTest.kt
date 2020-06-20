package com.insiderser.mars.data

import com.insiderser.mars.FakeImagesProvider
import com.insiderser.mars.data.db.MarsImagesDao
import com.insiderser.mars.data.remote.RemoteMarsImagesDataSource
import com.insiderser.mars.fake.FakeAppPreferencesStorage
import com.insiderser.mars.model.Sol
import com.insiderser.mars.model.plus
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MarsImagesLoaderTest {

    @MockK
    private lateinit var remoteDataSource: RemoteMarsImagesDataSource

    @RelaxedMockK
    private lateinit var dao: MarsImagesDao

    private val preferences = FakeAppPreferencesStorage()

    private lateinit var loader: MarsImagesLoader

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loader = MarsImagesLoader(remoteDataSource, dao, preferences, Schedulers.trampoline())
    }

    @Test
    fun `When nothing loaded onZeroItemsLoaded should load landing sol`() {
        preferences.lastLoadedSolarDay = null
        every { remoteDataSource.getImages(Sol.landing) } returns Single.just(FakeImagesProvider.list)

        loader.onZeroItemsLoaded()

        preferences.lastLoadedSolarDay shouldBeEqualTo Sol.landing
        verify { dao.insertAll(FakeImagesProvider.list) }
        confirmVerified(dao)
    }

    @Test
    fun `If loaded previously onItemAtEndLoaded should load next sol`() {
        preferences.lastLoadedSolarDay = Sol.landing
        every { remoteDataSource.getImages(Sol.landing.plus(1)) } returns Single.just(FakeImagesProvider.list)

        loader.onItemAtEndLoaded(mockk())

        preferences.lastLoadedSolarDay shouldBeEqualTo Sol.landing.plus(1)
        verify { dao.insertAll(FakeImagesProvider.list) }
        confirmVerified(dao)
    }

    @Test
    fun `If cannot fetch next item onItemAtEndLoaded should not load next sol`() {
        preferences.lastLoadedSolarDay = Sol.now

        loader.onItemAtEndLoaded(mockk())

        preferences.lastLoadedSolarDay shouldBeEqualTo Sol.now
        verify(exactly = 0) { remoteDataSource.getImages(any()) }
        verify(exactly = 0) { dao.insertAll(any()) }
    }

    @Test
    fun `If nothing loaded and remote returns error MarsImagesLoader should handle error`() {
        checkCanHandleErrors(null)
    }

    @Test
    fun `If loaded previously and remote returns error MarsImagesLoader should handle error`() {
        checkCanHandleErrors(Sol.landing)
    }

    private fun checkCanHandleErrors(sol: Sol?) {
        val nextSol = sol?.plus(1) ?: Sol.landing
        val error = IOException("Internet not available")
        every { remoteDataSource.getImages(nextSol) } returns Single.error(error)
        preferences.lastLoadedSolarDay = sol

        loader.onZeroItemsLoaded()

        preferences.lastLoadedSolarDay shouldBeEqualTo sol
        verify(exactly = 0) { dao.insertAll(any()) }

        // TODO: check error is propagated to UI.
    }
}
