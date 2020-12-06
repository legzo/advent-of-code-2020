package gg.jte.aoc

import java.io.File

fun getListOfIntsFromSingleLine(filename: String): List<Int> =
    getLinesFromFile(filename)
        .first()
        .split(" ")
        .map { it.toInt() }

fun getLinesFromFile(filename: String): List<String> =
    File("src/main/resources/$filename").readLines()

fun getTextFromFile(filename: String): String =
    File("src/main/resources/$filename").readText()

fun getLinesFromFileAsInts(filename: String): List<Int> =
    getLinesFromFile(filename)
        .map { it.toInt() }

fun shouldNotHappen(): Nothing = throw IllegalArgumentException("Should not happen !")