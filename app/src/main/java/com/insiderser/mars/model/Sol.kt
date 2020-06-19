package com.insiderser.mars.model

/**
 * Sol - Day on Mars.
 *
 * Represents amount of sols since Curiosity's landing.
 */
data class Sol(val value: Int) {

    companion object {

        @JvmField
        val LANDING = Sol(39)

        @JvmField
        val START = LANDING
    }
}

operator fun Sol.plus(days: Int): Sol {
    return Sol(this.value + days)
}
