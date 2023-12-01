package io.github.tomplum.aoc.weather.strategy

class DigitOnlyCalibration : CalibrationStrategy {
    override fun calibrate(document: List<String>): Int = document
        .map { it.filter { char -> char.isDigit() } }
        .map { it.first().toString() + it.last() }
        .sumOf { it.toInt() }
}