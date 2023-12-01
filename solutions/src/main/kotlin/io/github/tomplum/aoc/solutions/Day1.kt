package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.weather.WeatherMachine
import io.github.tomplum.aoc.weather.strategy.DigitOnlyCalibration
import io.github.tomplum.aoc.weather.strategy.WordAndDigitCalibration
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day1 : Solution<Int, Int> {

    private val input = InputReader.read<String>(Day(1)).value
    private val weatherMachine = WeatherMachine(input)

    override fun part1(): Int {
        val calibrationStrategy = DigitOnlyCalibration()
        return weatherMachine.calibrate(calibrationStrategy)
    }

    override fun part2(): Int {
        val calibrationStrategy = WordAndDigitCalibration()
        return weatherMachine.calibrate(calibrationStrategy)
    }
}
