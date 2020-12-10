package gg.jte.aoc

import gg.jte.aoc.Instruction.*

fun main() {

    val lines = getLinesFromFile("day_08.txt")
    val instructions = lines.parseAsInstructions()

    // Part 1
    println(instructions.executeUntilLoopAndReturnAccumulator())

}

sealed class Instruction {
    data class Jump(val offset: Int) : Instruction()
    data class Accumulate(val value: Int) : Instruction()
    object Noop : Instruction()
}
data class ExecutionStatus(
    val currentIndex: Int,
    val accumatorValue: Int,
    val alreadyVisitedInstructions: Set<Int>
) {

    fun execute(instruction: Accumulate) =
        ExecutionStatus(
            currentIndex = currentIndex + 1,
            accumatorValue = accumatorValue + instruction.value,
            alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
        )

    fun execute(instruction: Jump) =
        ExecutionStatus(
            currentIndex = currentIndex + instruction.offset,
            accumatorValue = accumatorValue,
            alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
        )

    fun noop() =
        ExecutionStatus(
            currentIndex = currentIndex + 1,
            accumatorValue = accumatorValue,
            alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
        )

    companion object {
        fun start() =
            ExecutionStatus(
                currentIndex = 0,
                accumatorValue = 0,
                alreadyVisitedInstructions = setOf()
            )
    }
}

fun List<Instruction>.executeUntilLoopAndReturnAccumulator(
    executionStatus: ExecutionStatus = ExecutionStatus.start()
): Int {
    val currentExecutionIndex = executionStatus.currentIndex

    if (currentExecutionIndex in executionStatus.alreadyVisitedInstructions)
        return executionStatus.accumatorValue

    return when (val currentInstruction = this[currentExecutionIndex]) {
        is Jump -> executeUntilLoopAndReturnAccumulator(executionStatus.execute(currentInstruction))
        is Accumulate -> executeUntilLoopAndReturnAccumulator(executionStatus.execute(currentInstruction))
        Noop -> executeUntilLoopAndReturnAccumulator(executionStatus.noop())
    }

}

fun List<String>.parseAsInstructions(): List<Instruction> =
    mapNotNull { line ->
        line.parseWithRegex("([\\w]+) ([\\+-][\\d]+)") { (actionAsString, value) ->
            when (actionAsString) {
                "nop" -> Noop
                "jmp" -> Jump(value.toInt())
                "acc" -> Accumulate(value.toInt())
                else -> null
            }
        }
    }
