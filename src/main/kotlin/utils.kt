import java.io.File

fun getListOfIntsFromSingleLine(filename: String): List<Int> =
    getLinesFromFile(filename)
        .first()
        .split(" ")
        .map { it.toInt() }

fun getLinesFromFile(filename: String): List<String> =
    File("src/main/resources/$filename").readLines()

fun getLinesFromFileAsInts(filename: String): List<Int> =
    getLinesFromFile(filename)
        .map { it.toInt() }
