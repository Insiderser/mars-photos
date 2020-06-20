package com.insiderser.mars.utils

import com.insiderser.mars.model.Sol
import com.insiderser.mars.model.isBefore
import com.insiderser.mars.model.plus
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId

object SolsConfig {

    val landing = Sol(39)

    val landingEarthDate = LocalDate.of(2012, Month.AUGUST, 6)

    val now: Sol
        get() {
            val utc = ZoneId.of("UTC")
            val nowOnEarth = LocalDate.now(utc)
            val earthDaysSinceLaunch = daysBetween(landingEarthDate, nowOnEarth)
            val solsSinceLaunch = earthDaysSinceLaunch / 1.02749125
            return Sol(solsSinceLaunch.toInt())
        }

    // NASA doesn't provide images immediately.
    fun canFetch(sol: Sol): Boolean = sol.plus(2).isBefore(now)
}
