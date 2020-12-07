package gg.jte.aoc

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
    fun `should calcultate seat coordinates`() {
        Seat.parse("BFFFBBFRRR") shouldBe Seat(row = 70, column = 7)
        Seat.parse("FFFBBBFRRR") shouldBe Seat(row = 14, column = 7)
        Seat.parse("BBFFBBFRLL") shouldBe Seat(row = 102, column = 4)
    }

    @Test
    fun `should calcultate seat ids`() {
        Seat.parse("BFFFBBFRRR").id shouldBe 567
        Seat.parse("FFFBBBFRRR").id shouldBe 119
        Seat.parse("BBFFBBFRLL").id shouldBe 820
    }

}
