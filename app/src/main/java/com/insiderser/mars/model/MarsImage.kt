package com.insiderser.mars.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mars_images")
data class MarsImage(
    @PrimaryKey val id: Int,
    val url: String,
)
