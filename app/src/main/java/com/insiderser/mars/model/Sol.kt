package com.insiderser.mars.model

import com.insiderser.mars.utils.daysBetween
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId

/**
 * Sol - Day on Mars.
 *
 * Represents amount of sols since Curiosity's landing.
 */
data class Sol(val value: Int) {

    init {
        require(value >= 0) { "Sol value cannot be negative." }
    }

    companion object {

        val landing = Sol(39)

        private val curiosityLandingEarthDate = LocalDate.of(2012, Month.AUGUST, 6)

        val now: Sol
            get() {
                val utc = ZoneId.of("UTC")
                val nowOnEarth = LocalDate.now(utc)
                val earthDaysSinceLaunch = daysBetween(curiosityLandingEarthDate, nowOnEarth)
                val solsSinceLaunch = earthDaysSinceLaunch / 1.02749125
                return Sol(solsSinceLaunch.toInt())
            }
    }
}

operator fun Sol.plus(days: Int): Sol {
    return Sol(this.value + days)
}

fun Sol.isBefore(other: Sol): Boolean =
    this.value < other.value

fun Sol.hasPassed(): Boolean = this.isBefore(Sol.now)
