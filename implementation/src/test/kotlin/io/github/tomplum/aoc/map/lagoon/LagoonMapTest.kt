package io.github.tomplum.aoc.map.lagoon

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class LagoonMapTest {
    private val digPlan = TestInputReader.read<String>("day18/example.txt").value
    private val lagoonMap = LagoonMap(digPlan)

    @Test
    fun partOneExampleOne() {
        assertThat(lagoonMap.calculateVolume()).isEqualTo(62)
    }
}