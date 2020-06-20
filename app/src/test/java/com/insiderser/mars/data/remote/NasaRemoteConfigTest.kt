package com.insiderser.mars.data.remote

import com.insiderser.mars.model.Sol
import com.insiderser.mars.model.plus
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NasaRemoteConfigTest {

    @Test
    fun `canFetch with current or future Sol returns false`() {
        NasaRemoteConfig.canFetch(Sol.now) shouldBeEqualTo false
        NasaRemoteConfig.canFetch(Sol.now.plus(100)) shouldBeEqualTo false
        NasaRemoteConfig.canFetch(Sol(40000000)) shouldBeEqualTo false
    }

    @Test
    fun `canFetch landing returns true`() {
        NasaRemoteConfig.canFetch(Sol.landing) shouldBeEqualTo true
    }
}
