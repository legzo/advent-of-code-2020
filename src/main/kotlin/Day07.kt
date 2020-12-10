package gg.jte.aoc

fun main() {

    val lines = getLinesFromFile("day_07.txt")

    val bagConstraints = lines
        .mapNotNull { line -> parseAsBagConstraint(line) }

    // Part 1
    println(bagConstraints.findAllContainersFor(Bag("shiny gold")).count())

    // Part 2
    println(bagConstraints.countAllPossibleContainedBagsFor(Bag("shiny gold")))
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
    val directContainers = findDirectContainersFor(bag)
    return directContainers + directContainers.flatMap { findAllContainersFor(it) }
}

fun List<BagConstraint>.countAllPossibleContainedBagsFor(bag: Bag): Int {
    return countDirectContainedBags(bag) +
            findContentsFor(bag)
                .sumBy { it.quantity * countAllPossibleContainedBagsFor(it.bag) }
}

private fun List<BagConstraint>.findContentsFor(bag: Bag) =
    first { it.container == bag }
        .contents

private fun List<BagConstraint>.findDirectContainersFor(bag: Bag) =
    filter { bag in it.bagContents }
        .map { it.container }
        .toSet()

private fun List<BagConstraint>.countDirectContainedBags(bag: Bag) =
    findContentsFor(bag)
        .sumBy { content -> content.quantity }
