package io.github.tomplum.aoc.game.race

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class BoatRaceSimulatorTest {
    private val input = TestInputReader.read<String>("day6/example.txt").value

    @Test
    fun examplePartOne() {
        val simulator = BoatRaceSimulator(input)
        assertThat(simulator.calculateWinningMethodQuantityProduct()).isEqualTo(288)
    }
}