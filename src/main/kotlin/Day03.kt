package gg.jte.aoc

fun main() {

    val input = getLinesFromFile("day_03.txt")

    // Part 1
    println(input.countTreesOnTheWay(Slope(x = 3, y = 1)))

    // Part 2
    println(input.findMultipliedTotalOfTreesFor(
            listOf(
                Slope(x = 1, y = 1),
                Slope(x = 3, y = 1),
                Slope(x = 5, y = 1),
                Slope(x = 7, y = 1),
                Slope(x = 1, y = 2)
            )
        ))
}

data class Slope(
    val x: Int,
    val y: Int
)

fun List<String>.findMultipliedTotalOfTreesFor(slopes: List<Slope>) =
    slopes.map { slope -> countTreesOnTheWay(slope).toLong() }
        .reduce { acc, current -> acc * current }

fun List<String>.countTreesOnTheWay(slope: Slope) =
    (0 until size step slope.y)
        .count { yPosition ->
            val currentLine = this[yPosition]
            val movementsCount = yPosition / slope.y
            val xPosition = (slope.x * movementsCount) % currentLine.length
            currentLine[xPosition].isTree()
        }

private fun Char.isTree() = this == '#'
