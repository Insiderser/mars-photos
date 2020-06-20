package com.insiderser.mars.fake

import com.insiderser.mars.data.AppPreferencesStorage
import com.insiderser.mars.model.Sol

class FakeAppPreferencesStorage : AppPreferencesStorage {

    override var lastLoadedSolarDay: Sol? = null
}
