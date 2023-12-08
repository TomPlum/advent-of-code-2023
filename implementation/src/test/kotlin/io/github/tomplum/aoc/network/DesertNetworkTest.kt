package io.github.tomplum.aoc.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import io.github.tomplum.aoc.network.strategy.EtherealNavigation
import io.github.tomplum.aoc.network.strategy.LeftRightNavigation
import org.junit.jupiter.api.Test

class DesertNetworkTest {
    private val network = DesertNetwork()

    @Test
    fun exampleOnePartOne() {
        val input = TestInputReader.read<String>("day8/example-1.txt")
        val strategy = LeftRightNavigation(input.value)
        assertThat(network.stepsRequiredToReachEnd(strategy)).isEqualTo(2)
    }

    @Test
    fun exampleTwoPartOne() {
        val input = TestInputReader.read<String>("day8/example-2.txt")
        val strategy = LeftRightNavigation(input.value)
        assertThat(network.stepsRequiredToReachEnd(strategy)).isEqualTo(6)
    }

    @Test
    fun exampleOnePartTwo() {
        val input = TestInputReader.read<String>("day8/example-3.txt")
        val strategy = EtherealNavigation(input.value)
        assertThat(network.stepsRequiredToReachEnd(strategy)).isEqualTo(6)
    }
}