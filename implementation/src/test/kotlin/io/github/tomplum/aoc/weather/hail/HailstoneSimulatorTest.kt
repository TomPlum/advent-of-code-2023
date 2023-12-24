package io.github.tomplum.aoc.weather.hail

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class HailstoneSimulatorTest {
    private val data = TestInputReader.read<String>("day24/example.txt").value
    private val simulator = HailstoneSimulator(data)

    @Test
    fun partOneExample() {
        assertThat(simulator.simulate(xTestArea = 7, yTestArea = 27)).isEqualTo(2)
    }

    @Test
    fun partTwoExample() {
        assertThat(simulator.throwStone()).isEqualTo(47)
    }
}