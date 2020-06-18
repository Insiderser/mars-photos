package com.insiderser.mars.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.insiderser.mars.model.MarsImage

@Dao
interface MarsImagesDao {

    @Query("SELECT * FROM mars_images")
    fun getImages(): DataSource.Factory<Int, MarsImage>

    @Insert
    fun insertAll(images: List<MarsImage>)
}
