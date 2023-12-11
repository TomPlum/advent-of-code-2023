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
        val distanceSum = cosmicMap.calculateGalacticDistancesAfterExpansion()
        assertThat(distanceSum).isEqualTo(374)
    }

    @Test
    fun exampleOnePartTwo() {
        val distanceSum = cosmicMap.calculateGalacticDistancesAfterExpansion(
            horizontalExpansion = 10,
            verticalExpansion = 10
        )
        assertThat(distanceSum).isEqualTo(1030)
    }

    @Test
    fun exampleTwoPartTwo() {
        val distanceSum = cosmicMap.calculateGalacticDistancesAfterExpansion(
            horizontalExpansion = 100,
            verticalExpansion = 100
        )
        assertThat(distanceSum).isEqualTo(8410)
    }
}