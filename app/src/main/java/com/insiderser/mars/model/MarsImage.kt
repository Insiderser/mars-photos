package com.insiderser.mars.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "mars_images")
data class MarsImage(
    @PrimaryKey val id: Int,
    val url: String,
    val sol: SolarDaysSinceLanding,
)

object SolTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromSol(sol: SolarDaysSinceLanding?): Int? = sol?.value

    @TypeConverter
    @JvmStatic
    fun toSol(value: Int?): SolarDaysSinceLanding? = value?.let { SolarDaysSinceLanding(it) }
}
