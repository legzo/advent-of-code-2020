package gg.jte.aoc

fun main() {
    val input = getLinesFromFile("day_03.txt")

    println(input.countTreesOnTheWay())
}

fun List<String>.countTreesOnTheWay(): Int {
    val xMovement = 3
    return this
        .mapIndexed { index, line -> line[(xMovement * index) % line.length] }
        .count { it == '#' }
}
