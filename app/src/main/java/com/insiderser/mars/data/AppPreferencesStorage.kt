package com.insiderser.mars.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.insiderser.mars.model.SolarDaysSinceLanding
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface AppPreferencesStorage {

    var lastLoadedSolarDay: SolarDaysSinceLanding?
}

class DefaultAppPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context
) : AppPreferencesStorage {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    }

    override var lastLoadedSolarDay: SolarDaysSinceLanding?
        get() =
            if (sharedPreferences.contains(PREFERENCE_LAST_LOADED_PAGE)) {
                val value = sharedPreferences.getInt(PREFERENCE_LAST_LOADED_PAGE, -1)
                SolarDaysSinceLanding(value)
            } else null
        set(value) {
            sharedPreferences.edit {
                if (value != null) {
                    putInt(PREFERENCE_LAST_LOADED_PAGE, value.value)
                } else {
                    remove(PREFERENCE_LAST_LOADED_PAGE)
                }
            }
        }

    companion object {

        private const val PREFERENCES_NAME = "mars-images-preferences"

        private const val PREFERENCE_LAST_LOADED_PAGE = "last-loaded-page"
    }
}

@Module
@InstallIn(ApplicationComponent::class)
@Suppress("unused")
interface AppPreferencesStorageModule {

    @Binds
    @Singleton
    fun bindAppPreferencesStorage(default: DefaultAppPreferencesStorage): AppPreferencesStorage
}
