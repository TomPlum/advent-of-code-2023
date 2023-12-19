package io.github.tomplum.aoc.machine

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class MachinePartsSorterTest {
    private val testWorkflowData = TestInputReader.read<String>("day19/example.txt").value
    private val machinePartsSorter = MachinePartsSorter(testWorkflowData)

    @Test
    fun partOneExampleOne() {
        assertThat(machinePartsSorter.sort()).isEqualTo(19114)
    }
}