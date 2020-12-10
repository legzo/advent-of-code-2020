package gg.jte.aoc

fun main() {

    val lines = getLinesFromFile("day_07.txt")

    val bagConstraints = lines
        .mapNotNull { line -> parseAsBagConstraint(line) }

    // Part 1
    println(println(bagConstraints.findAllContainersFor(Bag("shiny gold")).count()))

    // Part 2
    println(

    )
}

data class Bag(val color: String)

data class BagConstraint(
    val container: Bag,
    val contents: List<Content>
) {
    val bagContents = contents.map { it.bag }
}

data class Content(
    val quantity: Int,
    val bag: Bag
)

fun parseAsBagConstraint(line: String): BagConstraint? {

    fun String.parseContent() =
        parseWithRegex("([\\d]+) ([\\w]+ [\\w]+) bags?") { (quantity, bagColor) ->
            Content(quantity = quantity.toInt(), bag = Bag(bagColor))
        }

    return line.parseWithRegex("([\\w]+ [\\w]+) bags contain (.*)") { (containerColor, contentsAsString) ->
        val contents = contentsAsString
            .split(',', '.')
            .mapNotNull { contentAsString -> contentAsString.trim().parseContent() }

        BagConstraint(container = Bag(containerColor), contents = contents)
    }
}

fun List<BagConstraint>.findAllContainersFor(bag: Bag): Set<Bag> {
    val directContainers = findDirectContainersFor(bag, this)
    return directContainers + directContainers.flatMap { this.findAllContainersFor(it) }
}

private fun findDirectContainersFor(
    bag: Bag,
    bagConstraints: List<BagConstraint>
) = bagConstraints
    .filter { bag in it.bagContents }
    .map { it.container }
    .toSet()