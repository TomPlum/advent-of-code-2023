package io.github.tomplum.aoc.weather.strategy

data class CalibrationValue(val index: Int, val value: Any)

class WordAndDigitCalibration : CalibrationStrategy {
    private val words = mapOf(
        "one" to 1, "two" to 2, "three" to 3, "four" to 4,
        "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )

    override fun calibrate(document: List<String>): Int = document.sumOf { calibrationString ->
        val firstDigit = calibrationString
            .firstOrNull { value -> value.isDigit() }
            ?.let { value -> CalibrationValue(calibrationString.indexOf(value), value) }

        val firstWord = words.keys
            .filter { word -> calibrationString.contains(word) }
            .map { word -> CalibrationValue(calibrationString.indexOf(word), words[word]!!) }
            .minByOrNull { calibrationValue -> calibrationValue.index }

        val lastDigit = calibrationString
            .lastOrNull { value -> value.isDigit() }
            ?.let { value -> CalibrationValue(calibrationString.lastIndexOf(value), value) }

        val lastWord = words.keys
            .filter { word -> calibrationString.contains(word) }
            .map { word -> CalibrationValue(calibrationString.lastIndexOf(word), words[word]!!) }
            .maxByOrNull { calibrationValue -> calibrationValue.index }

        val first = listOf(firstDigit, firstWord)
            .minByOrNull { calibrationValue -> calibrationValue?.index ?: Int.MAX_VALUE }!!
            .value

        val last = listOf(lastDigit, lastWord)
            .maxByOrNull { calibrationValue -> calibrationValue?.index ?: Int.MIN_VALUE }!!
            .value

        "$first$last".toInt()
    }
}