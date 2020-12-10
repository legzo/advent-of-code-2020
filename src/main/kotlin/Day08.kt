package gg.jte.aoc

import gg.jte.aoc.ExecutionState.Companion.start
import gg.jte.aoc.Instruction.*
import gg.jte.aoc.Termination.CorrectTermination
import gg.jte.aoc.Termination.InfiniteLoop

fun main() {

    val lines = getLinesFromFile("day_08.txt")
    val instructions = lines.parseAsInstructions()

    // Part 1
    measureTimeAndPrint { instructions.execute().lastAccumulatorValue }

    // Part 2
    measureTimeAndPrint { instructions.fixAndExecute().lastAccumulatorValue }

}

sealed class Instruction {
    data class Jump(val offset: Int) : Instruction()
    data class Accumulate(val value: Int) : Instruction()

    // Noop should really be an object, but value is used in part 2 for mutating to Jump
    data class Noop(val value: Int) : Instruction()
}

sealed class Termination {
    abstract val lastAccumulatorValue: Int

    data class InfiniteLoop(override val lastAccumulatorValue: Int) : Termination()
    data class CorrectTermination(override val lastAccumulatorValue: Int) : Termination()
}

data class ExecutionState(
    val currentIndex: Int,
    val accumatorValue: Int,
    val alreadyVisitedInstructions: Set<Int>
) {

    fun apply(instruction: Accumulate) =
        ExecutionState(
            currentIndex = currentIndex + 1,
            accumatorValue = accumatorValue + instruction.value,
            alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
        )

    fun apply(instruction: Jump) =
        ExecutionState(
            currentIndex = currentIndex + instruction.offset,
            accumatorValue = accumatorValue,
            alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
        )

    fun noop() =
        ExecutionState(
            currentIndex = currentIndex + 1,
            accumatorValue = accumatorValue,
            alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
        )

    companion object {
        val start =
            ExecutionState(
                currentIndex = 0,
                accumatorValue = 0,
                alreadyVisitedInstructions = setOf()
            )
    }
}

fun List<Instruction>.fixAndExecute(): Termination {

    val allMutations: List<List<Instruction>> =
        this.mapIndexedNotNull { index, instruction ->
            when (instruction) {
                is Jump -> this.copyAndReplaceAtIndexWith(index, Noop(instruction.offset))
                is Noop -> this.copyAndReplaceAtIndexWith(index, Jump(instruction.value))
                is Accumulate -> null // output is not mutated so we skip this variation
            }
        }

    return allMutations
        .asSequence() // in order to return early -> 190ms vs 250ms 😅...
        .map { it.execute() }
        .first { it is CorrectTermination }
}

fun List<Instruction>.execute(
    executionState: ExecutionState = start
): Termination {
    val currentExecutionIndex = executionState.currentIndex

    if (currentExecutionIndex in executionState.alreadyVisitedInstructions)
        return InfiniteLoop(executionState.accumatorValue)

    if (currentExecutionIndex == this.size)
        return CorrectTermination(executionState.accumatorValue)

    return when (val currentInstruction = this[currentExecutionIndex]) {
        is Jump -> execute(executionState.apply(currentInstruction))
        is Accumulate -> execute(executionState.apply(currentInstruction))
        is Noop -> execute(executionState.noop())
    }

}

fun List<String>.parseAsInstructions(): List<Instruction> =
    mapNotNull { line ->
        line.parseWithRegex("([\\w]+) ([\\+-][\\d]+)") { (actionAsString, valueAsString) ->
            val value = valueAsString.toInt()
            when (actionAsString) {
                "nop" -> Noop(value)
                "jmp" -> Jump(value)
                "acc" -> Accumulate(value)
                else -> null
            }
        }
    }

private fun List<Instruction>.copyAndReplaceAtIndexWith(index: Int, newInstruction: Instruction) =
    this.toMutableList()
        .apply { this[index] = newInstruction }
        .toList()
