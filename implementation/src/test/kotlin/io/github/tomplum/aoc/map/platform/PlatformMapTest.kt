package io.github.tomplum.aoc.map.platform

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class PlatformMapTest {
    private val data = TestInputReader.read<String>("day14/example.txt").value
    private val platformMap = PlatformMap(data)

    @Test
    fun examplePartOne() {
        assertThat(platformMap.calculateNorthSupportBeamsTotalLoad()).isEqualTo(136)
    }

    @Test
    fun examplePartTwo() {
        assertThat(platformMap.calculateNorthSupportBeamsTotalLoad(cycles = 1000000000)).isEqualTo(64)
    }
}