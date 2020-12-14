package gg.jte.aoc

fun main() {

    val numbers = getLinesFromFileAsInts("day_10.txt")

    // Part 1
    measureTimeAndPrint { numbers
        .findCompatibleAdaptersList()
        .getVoltageDifferences()
        .values
        .reduce(Int::times)
    }

    // Part 2
    measureTimeAndPrint { }

}

fun List<Int>.findCompatibleAdaptersList(): List<Int> {
    return this.sorted()
}

fun List<Int>.getVoltageDifferences(): Map<Int, Int> =
    (listOf(0) + this)
        .zipWithNext { current, next -> next - current }
        .plus(3)
        .groupingBy { it }
        .eachCount()
