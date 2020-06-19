package com.insiderser.mars.utils

import com.insiderser.mars.model.Sol
import com.insiderser.mars.model.isBefore
import com.insiderser.mars.model.plus
import java.time.LocalDate
import java.time.Month

object SolsConfig {

    val landing = Sol(39)

    val landingEarthDate = LocalDate.of(2012, Month.AUGUST, 6)

    val now: Sol
        get() {
            val nowOnEarth = LocalDate.now()
            val earthDaysSinceLaunch = daysBetween(landingEarthDate, nowOnEarth)
            val solsSinceLaunch = earthDaysSinceLaunch / 1.02749125
            return Sol(solsSinceLaunch.toInt())
        }

    // NASA doesn't provide images immediately.
    // TODO: test plus days amount. Take into account different Earth time zones.
    fun canFetch(sol: Sol): Boolean = sol.plus(2).isBefore(now)
}
