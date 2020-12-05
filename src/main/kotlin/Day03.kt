package gg.jte.aoc

fun main() {

    val input = getLinesFromFile("day_03.txt")

    // Part 1
    println(input.countTreesOnTheWay(Slope(x = 3, y = 1)))

}

data class Slope(
    val x: Int,
    val y: Int
)

fun List<String>.countTreesOnTheWay(slope: Slope) =
    (0 until size step slope.y)
        .count { yPosition ->
            val line = this[yPosition]
            val xPosition = (slope.x * yPosition) % line.length
            line[xPosition].isTree()
        }

private fun Char.isTree() = this == '#'
