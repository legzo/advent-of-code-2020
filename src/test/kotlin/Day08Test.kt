package gg.jte.aoc

import gg.jte.aoc.Instruction.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

    private val input = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
        """.trimIndent()

    private val inputLines = input
        .split('\n')

    @Test
    fun `should parse instructions correctly`() {
        inputLines.parseAsInstructions() shouldBe listOf(
            Noop,
            Accumulate(1),
            Jump(4),
            Accumulate(3),
            Jump(-3),
            Accumulate(-99),
            Accumulate(1),
            Jump(-4),
            Accumulate(6),
        )
    }

    @Test
    fun `should get accumulator value just before loop`() {
        inputLines
            .parseAsInstructions()
            .executeUntilLoopAndReturnAccumulator() shouldBe 5
    }

}
