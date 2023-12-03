package io.github.tomplum.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.engine.Engine
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class EngineTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("day3/example-1.txt").value
        val partNumbers = Engine(input).determinePartNumbers()
        assertThat(partNumbers).isEqualTo(4361)
    }

    @Test
    fun partTwoExample() {
        val input = TestInputReader.read<String>("day3/example-1.txt").value
        val gearRatio = Engine(input).determineGearRatio()
        assertThat(gearRatio).isEqualTo(467835)
    }
}