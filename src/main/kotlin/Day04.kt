package gg.jte.aoc


fun main() {
    val input = getTextFromFile("day_04.txt")

    // Part 1
    println(input.countValidPassportsItems())

    // Part 2
    println(input.countValidPassportsItemsWithFieldsValidation())
}


enum class PassportField(
    val description: String,
    val shortLabel: String,
    val validate: (String) -> Boolean,
    val optional: Boolean = false,
) {
    BirthYear("Birth Year", "byr", validate = isIntBetween(1920, 2002)),
    IssueYear("Issue Year", "iyr", validate = isIntBetween(2010, 2020)),
    ExpirationYear("Expiration Year", "eyr", validate = isIntBetween(2020, 2030)),
    Height("Height", "hgt", validate = {
        val matchResult = PassportField.heightRegex.matchEntire(it)
        if (matchResult != null) {
            val (heightValue, unit) = matchResult.destructured
            when (unit) {
                "cm" -> isIntBetween(150, 193)(heightValue)
                "in" -> isIntBetween(59, 76)(heightValue)
                else -> false
            }
        } else false
    }),
    HairColor("Hair Color", "hcl", validate = { PassportField.hairColorRegex.matches(it) }),
    EyeColor("Eye Color", "ecl", validate = { it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") }),
    PassportId("Passport ID", "pid", validate = { PassportField.passportIdRegex.matches(it) }),
    CountryId("Country ID", "cid", validate = { true }, optional = true);

    companion object {
        val heightRegex = Regex("([0-9]+)(cm|in)")
        val hairColorRegex = Regex("#[0-9a-f]{6}")
        val passportIdRegex = Regex("[0-9]{9}")

        fun byShortLabel(shortLabel: String): PassportField? =
            values().firstOrNull { it.shortLabel == shortLabel }
    }
}

fun String.countValidPassportsItems() =
    parsePassports()
        .count { it.isStructurallyValid() }

fun String.countValidPassportsItemsWithFieldsValidation() =
    parsePassports()
        .count {
            it.isStructurallyValid() && it.allFieldsAreValid()
        }

fun String.parsePassports() =
    this.split("\n\n")
        .map { passportAsString ->
            Passport(fields = extractFields(passportAsString))
        }


private fun isIntBetween(lowerBound: Int, upperBound: Int): (String) -> Boolean {
    return { value -> value.toIntOrNull() in lowerBound..upperBound }
}

private fun extractFields(passportAsString: String) =
    passportAsString
        .split(' ', '\n')
        .mapNotNull {
            val keyValues = it.split(':')
            if (keyValues.size != 2) return@mapNotNull null
            val key = keyValues[0]
            val value = keyValues[1]
            val field = PassportField.byShortLabel(key)
            if (field != null) {
                field to value
            } else null
        }.toMap()

data class Passport(
    val fields: Map<PassportField, String>
) {
    fun isStructurallyValid() =
        PassportField.values()
            .all { it.optional || (fields[it]?.isNotBlank() ?: false) }

    fun allFieldsAreValid() =
        PassportField.values()
            .all {
                it.optional
                        || fields[it]?.let { value -> it.validate(value) } ?: false
            }
}
