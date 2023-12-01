package io.github.tomplum.aoc.weather.strategy

class WordAndDigitCalibration : CalibrationStrategy {
    private val validNumbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    override fun calibrate(document: List<String>): Int = document.map { calibrationValue ->
        val firstDigit = calibrationValue
            .firstOrNull { value -> value.isDigit() }
            ?.let { value -> value.toString() to calibrationValue.indexOf(value) }

        val firstWord = validNumbers.keys.mapNotNull { word ->
            if (calibrationValue.contains(word)) {
                return@mapNotNull validNumbers[word]!!.toString() to calibrationValue.indexOf(word)
            }

            null
        }.minByOrNull { (_, index) -> index }

        val lastDigit = calibrationValue
            .lastOrNull { value -> value.isDigit() }
            ?.let { value -> value.toString() to calibrationValue.lastIndexOf(value) }

        val lastWord = validNumbers.keys.mapNotNull { key ->
            if (calibrationValue.contains(key)) {
                return@mapNotNull validNumbers[key]!!.toString() to calibrationValue.lastIndexOf(key)
            }

            null
        }.maxByOrNull { (_, index) -> index }

        val first = listOf(firstDigit, firstWord).minBy { it?.second ?: Int.MAX_VALUE }!!.first
        val last = listOf(lastDigit, lastWord).maxBy { it?.second ?: Int.MIN_VALUE }!!.first

        "$first$last".toInt()
    }.sum()
}