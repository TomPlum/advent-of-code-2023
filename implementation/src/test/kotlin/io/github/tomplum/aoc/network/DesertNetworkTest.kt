package io.github.tomplum.aoc.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class DesertNetworkTest {
    @Test
    fun exampleOnePartOne() {
        val input = TestInputReader.read<String>("day8/example-1.txt")
        val network = DesertNetwork(input.value)
        assertThat(network.stepsRequiredToReachEnd()).isEqualTo(2)
    }

    @Test
    fun exampleTwoPartOne() {
        val input = TestInputReader.read<String>("day8/example-2.txt")
        val network = DesertNetwork(input.value)
        assertThat(network.stepsRequiredToReachEnd()).isEqualTo(6)
    }
}