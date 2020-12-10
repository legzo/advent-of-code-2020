package gg.jte.aoc

fun main() {

    val input = getTextFromFile("day_06.txt")

    // Part 1
    measureTimeAndPrint {
        input
            .parseGroups()
            .sumBy { it.numberOfQuestionsAnsweredWithYesByAtLeastOnePerson }
    }

    // Part 2
    measureTimeAndPrint {
        input
            .parseGroups()
            .sumBy { it.numberOfQuestionsAnsweredWithYesByEveryone }
    }
}

fun String.parseGroups(): List<Group> =
    this
        .split("\n\n")
        .map { groupResultsAsString ->
            Group(results = groupResultsAsString.split("\n"))
        }


data class Group(
    val results: List<String>
) {
    val numberOfQuestionsAnsweredWithYesByAtLeastOnePerson =
        results.flatMap { it.toSet() }.toSet().count()

    val numberOfQuestionsAnsweredWithYesByEveryone =
        ('a'..'z').count { char ->
            results.all { char in it }
        }
}
