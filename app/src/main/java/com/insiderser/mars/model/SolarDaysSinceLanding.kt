package com.insiderser.mars.model

inline class SolarDaysSinceLanding(val value: Int) {

    companion object {

        val LANDING = SolarDaysSinceLanding(0)
        val START = LANDING
    }
}

operator fun SolarDaysSinceLanding.plus(days: Int): SolarDaysSinceLanding {
    return SolarDaysSinceLanding(this.value + days)
}
