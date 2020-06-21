package com.insiderser.mars.model

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.assertThrows
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class SolTest {

    @Test
    fun `ofDate is correct`() {
        val data = mapOf(
            LocalDate.of(2020, Month.JANUARY, 1) to Sol(2631),
            LocalDate.of(2025, Month.AUGUST, 6) to Sol(4620),
        )

        data.forEach { (date, sol) ->
            Sol.ofDate(date) shouldBeEqualTo sol
        }
    }

    @Test
    fun `+ is correct`() {
        Sol(4).plus(1) shouldBeEqualTo Sol(5)
        Sol(4).plus(100) shouldBeEqualTo Sol(104)
        Sol(2000).plus(-100) shouldBeEqualTo Sol(1900)

        assertThrows(IllegalArgumentException::class.java) {
            Sol(4).plus(-100)
        }
    }

    @Test
    fun `isBefore is correct`() {
        Sol.LANDING.isBefore(Sol.LANDING.plus(1)) shouldBeEqualTo true
        Sol.LANDING.isBefore(Sol.LANDING) shouldBeEqualTo false
        Sol(2000).isBefore(Sol(Int.MAX_VALUE)) shouldBeEqualTo true
        Sol(Int.MAX_VALUE).isBefore(Sol(2000)) shouldBeEqualTo false
        Sol(Int.MAX_VALUE).isBefore(Sol(Int.MAX_VALUE)) shouldBeEqualTo false
    }

    @Test
    fun `hasPassed is correct`() {
        Sol.LANDING.hasPassed() shouldBeEqualTo true
        Sol(Int.MAX_VALUE).hasPassed() shouldBeEqualTo false
        Sol.now().hasPassed() shouldBeEqualTo false
        Sol.now().plus(-1).hasPassed() shouldBeEqualTo true
    }

    @Test
    fun `Sol with negative value throws Exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            Sol(-1)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Sol(Int.MIN_VALUE)
        }
    }
}
