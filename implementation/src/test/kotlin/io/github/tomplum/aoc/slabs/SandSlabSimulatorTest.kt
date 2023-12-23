package io.github.tomplum.aoc.slabs

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class SandSlabSimulatorTest {
    private val snapshot = TestInputReader.read<String>("day22/example.txt").value
    private val simulator = SandSlabSimulator(snapshot)

    @Test
    fun partOneExample() {
        assertThat(simulator.calculateDisintegratableBricks()).isEqualTo(5)
    }

    @Test
    fun partTwoExample() {
        assertThat(simulator.simulateChainReaction()).isEqualTo(7)
    }
}