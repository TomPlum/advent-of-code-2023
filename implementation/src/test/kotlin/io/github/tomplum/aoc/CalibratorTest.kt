package io.github.tomplum.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CalibratorTest {
    private val input = TestInputReader.read<String>("day1/example-1.txt")
    private val calibrator = Calibrator(input.value)

    @Test
    fun examplePartOne() {
        assertThat(calibrator.calibrate()).isEqualTo(142)
    }
}