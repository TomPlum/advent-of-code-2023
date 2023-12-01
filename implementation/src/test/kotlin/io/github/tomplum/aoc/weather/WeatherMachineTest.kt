package io.github.tomplum.aoc.weather

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import io.github.tomplum.aoc.weather.strategy.DigitOnlyCalibration
import io.github.tomplum.aoc.weather.strategy.WordAndDigitCalibration
import org.junit.jupiter.api.Test

class WeatherMachineTest {

    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("day1/example-1.txt")
        val weatherMachine = WeatherMachine(input.value)
        val calibrationValueSum = weatherMachine.calibrate(DigitOnlyCalibration())
        assertThat(calibrationValueSum).isEqualTo(142)
    }

    @Test
    fun examplePartTwo() {
        val input = TestInputReader.read<String>("day1/example-2.txt")
        val weatherMachine = WeatherMachine(input.value)
        val calibrationValueSum = weatherMachine.calibrate(WordAndDigitCalibration())
        assertThat(calibrationValueSum).isEqualTo(281)
    }
}