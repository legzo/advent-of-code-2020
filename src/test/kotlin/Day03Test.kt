package gg.jte.aoc

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun `should count trees on the way`() {
        val input = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#""".trimIndent()

        input
            .split("\n")
            .countTreesOnTheWay() shouldBe 7
    }

}



