package io.github.tomplum.aoc.map.garden

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class GardenMapTest {
    private val data = TestInputReader.read<String>("day21/example.txt").value
    private val gardenMap = GardenMap(data)

    @Test
    fun partOneExampleOne() {
        assertThat(gardenMap.traverse(6)).isEqualTo(16)
    }
}