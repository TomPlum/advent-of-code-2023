package io.github.tomplum.aoc.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CosmicMapTest {
    private val data = TestInputReader.read<String>("day11/example.txt").value
    private val cosmicMap = CosmicMap(data)

    @Test
    fun examplePartOne() {
        assertThat(cosmicMap.calculateGalacticDistancesAfterExpansion()).isEqualTo(374)
    }
}