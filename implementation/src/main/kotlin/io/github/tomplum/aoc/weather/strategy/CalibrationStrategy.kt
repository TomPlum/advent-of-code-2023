package io.github.tomplum.aoc.weather.strategy

interface CalibrationStrategy {
    fun calibrate(document: List<String>): Int
}