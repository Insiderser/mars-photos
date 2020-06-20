package com.insiderser.mars.utils

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class DaysKtTest {

    @Test
    fun `daysBetween is correct`() {
        val date = LocalDate.of(2020, Month.JANUARY, 4)

        daysBetween(date, date.plusDays(10)) shouldBeEqualTo 10
        daysBetween(date, date.plusMonths(1)) shouldBeEqualTo 31
        daysBetween(date, date.plusMonths(1).plusDays(40)) shouldBeEqualTo 71
        daysBetween(date.plusMonths(1).plusDays(40), date) shouldBeEqualTo -71
    }
}
