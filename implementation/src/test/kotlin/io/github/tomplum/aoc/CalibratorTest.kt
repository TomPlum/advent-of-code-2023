package io.github.tomplum.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CalibratorTest {

    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("day1/example-1.txt")
        val calibrator = Calibrator(input.value)
        assertThat(calibrator.calibrate()).isEqualTo(142)
    }

    @Test
    fun examplePartTwo() {
        val input = TestInputReader.read<String>("day1/example-2.txt")
        val calibrator = Calibrator(input.value)
        assertThat(calibrator.calibrateWithWords()).isEqualTo(281)
    }
}