package com.insiderser.mars.model

data class SolarDaysSinceLanding(val value: Int) {

    companion object {

        @JvmField
        val LANDING = SolarDaysSinceLanding(39)

        @JvmField
        val START = LANDING
    }
}

operator fun SolarDaysSinceLanding.plus(days: Int): SolarDaysSinceLanding {
    return SolarDaysSinceLanding(this.value + days)
}
