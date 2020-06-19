package com.insiderser.mars.utils

import java.time.Duration
import java.time.LocalDate

fun daysBetween(first: LocalDate, second: LocalDate): Long =
    Duration.between(first.atStartOfDay(), second.atStartOfDay()).toDays()
