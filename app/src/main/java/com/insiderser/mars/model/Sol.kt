package com.insiderser.mars.model

/**
 * Sol - Day on Mars.
 *
 * Represents amount of sols since Curiosity's landing.
 */
data class Sol(val value: Int)

operator fun Sol.plus(days: Int): Sol {
    return Sol(this.value + days)
}

fun Sol.isBefore(other: Sol): Boolean =
    this.value < other.value
