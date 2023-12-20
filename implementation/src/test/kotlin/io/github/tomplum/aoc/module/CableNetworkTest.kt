package io.github.tomplum.aoc.module

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CableNetworkTest {
    @Test
    fun partOneExampleOne() {
        val config = TestInputReader.read<String>("day20/example-1.txt").value
        val cableNetwork = SandMachineModuleNetwork(config)
        assertThat(cableNetwork.getPulseCount()).isEqualTo(32000000)
    }

    @Test
    fun partOneExampleTwo() {
        val config = TestInputReader.read<String>("day20/example-2.txt").value
        val cableNetwork = SandMachineModuleNetwork(config)
        assertThat(cableNetwork.getPulseCount()).isEqualTo(11687500)
    }
}