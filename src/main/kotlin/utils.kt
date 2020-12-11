package gg.jte.aoc

import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun getLinesFromFile(filename: String): List<String> =
    File("src/main/resources/$filename").readLines()

fun getTextFromFile(filename: String): String =
    File("src/main/resources/$filename").readText()

fun getLinesFromFileAsInts(filename: String): List<Int> =
    getLinesFromFile(filename)
        .map { it.toInt() }

fun getLinesFromFileAsLongs(filename: String): List<Long> =
    getLinesFromFile(filename)
        .map { it.toLong() }

fun shouldNotHappen(): Nothing = throw IllegalArgumentException("Should not happen !")

@OptIn(ExperimentalTime::class)
fun <T> measureTimeAndPrint(block: () -> T) {
    val (result, duration) = measureTimedValue(block)
    println("[${duration.inMilliseconds.toInt()}ms] Result is $result")
}

fun <T> String.parseWithRegex(
    regex: String,
    mapResult: (MatchResult.Destructured) -> T?
): T? {
    val matchResult = Regex(regex).matchEntire(this)
    return if (matchResult != null) {
        mapResult(matchResult.destructured)
    } else null
}
