package io.github.tomplum.aoc.game.race

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.game.race.strategy.MultipleRaceStrategy
import io.github.tomplum.aoc.game.race.strategy.SingleRaceStrategy
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class BoatRaceSimulatorTest {
    private val document = TestInputReader.read<String>("day6/example.txt").value
    private val simulator = BoatRaceSimulator()

    @Test
    fun examplePartOne() {
        val strategy = MultipleRaceStrategy(document)
        val winningMethodsProduct = simulator.calculateWinningMethods(strategy)
        assertThat(winningMethodsProduct).isEqualTo(288)
    }

    @Test
    fun examplePartTwo() {
        val strategy = SingleRaceStrategy(document)
        val winningMethods = simulator.calculateWinningMethods(strategy)
        assertThat(winningMethods).isEqualTo(71503)
    }
}