package io.github.tomplum.aoc.weather

data class CalibrationCandidatePair(val digit: CalibrationValue?, val word: CalibrationValue?) {
    fun getFirstCalibrationDigit(): String = listOf(digit, word)
        .minByOrNull { calibrationValue -> calibrationValue?.index ?: Int.MAX_VALUE }!!
        .value
        .toString()

    fun getLastCalibrationDigit(): String = listOf(digit, word)
        .maxByOrNull { calibrationValue -> calibrationValue?.index ?: Int.MIN_VALUE }!!
        .value
        .toString()
}