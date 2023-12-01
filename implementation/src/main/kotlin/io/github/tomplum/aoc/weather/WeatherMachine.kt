package io.github.tomplum.aoc.weather

import io.github.tomplum.aoc.weather.strategy.CalibrationStrategy

class WeatherMachine(private val calibrationDocument: List<String>) {
    fun calibrate(strategy: CalibrationStrategy): Int {
        return strategy.calibrate(calibrationDocument)
    }
}