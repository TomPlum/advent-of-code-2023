package io.github.tomplum.aoc.weather.strategy

data class CalibrationValue(val index: Int, val value: Any) {
    companion object {
    }
}

class WordAndDigitCalibration : CalibrationStrategy {
    private val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    override fun calibrate(document: List<String>): Int = document.sumOf { calibrationString ->
        val firstDigit = calibrationString.findFirstDigit { value -> calibrationString.indexOf(value) }
        val firstWord = calibrationString.findFirstWord()

        val lastDigit = calibrationString.findLastDigit { value -> calibrationString.lastIndexOf(value) }
        val lastWord = calibrationString.findLastWord()

        val first = listOf(firstDigit, firstWord)
            .minByOrNull { calibrationValue -> calibrationValue?.index ?: Int.MAX_VALUE }!!
            .value

        val last = listOf(lastDigit, lastWord)
            .maxByOrNull { calibrationValue -> calibrationValue?.index ?: Int.MIN_VALUE }!!
            .value

        "$first$last".toInt()
    }

    private fun String.findFirstDigit(indexPredicate: (char: Char) -> Int): CalibrationValue? = this
        .firstOrNull { value -> value.isDigit() }
        ?.let(function(indexPredicate))

    private fun String.findLastDigit(indexPredicate: (char: Char) -> Int): CalibrationValue? = this
        .lastOrNull { value -> value.isDigit() }
        ?.let(function(indexPredicate))

    private fun function(indexPredicate: (char: Char) -> Int) =
        { value: Char -> CalibrationValue(indexPredicate(value), value) }

    private fun String.findLastWord(): CalibrationValue? = words
        .filter { word -> this.contains(word) }
        .map { word -> CalibrationValue(this.lastIndexOf(word), getWordValue(word)) }
        .maxByOrNull { calibrationValue -> calibrationValue.index }

    private fun String.findFirstWord(): CalibrationValue? = words
        .filter { word -> this.contains(word) }
        .map { word -> CalibrationValue(this.indexOf(word), getWordValue(word)) }
        .minByOrNull { calibrationValue -> calibrationValue.index }

    private fun getWordValue(word: String) = words.indexOf(word) + 1
}