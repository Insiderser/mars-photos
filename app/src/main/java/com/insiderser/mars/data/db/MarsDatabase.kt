package com.insiderser.mars.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.model.SolTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Database(
    entities = [
        MarsImage::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(SolTypeConverter::class)
abstract class MarsDatabase : RoomDatabase() {

    abstract val imagesDao: MarsImagesDao
}

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "mars.db"

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMarsDatabase(@ApplicationContext context: Context): MarsDatabase =
        Room.databaseBuilder(context, MarsDatabase::class.java, DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideImagesDao(db: MarsDatabase): MarsImagesDao = db.imagesDao
}
