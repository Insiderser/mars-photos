package com.insiderser.mars.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "mars_images")
data class MarsImage(
    @PrimaryKey val id: Int,
    val url: String,
    val sol: Sol,
)

object SolTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromSol(sol: Sol?): Int? = sol?.value

    @TypeConverter
    @JvmStatic
    fun toSol(value: Int?): Sol? = value?.let { Sol(it) }
}
