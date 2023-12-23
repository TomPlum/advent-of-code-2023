package io.github.tomplum.aoc.map.forest

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ForestMapTest {
    private val data = TestInputReader.read<String>("day23/example.txt").value
    private val forestMap = ForestMap(data)

    @Test
    fun partOneExample() {
        assertThat(forestMap.longestHikeSteps()).isEqualTo(94)
    }
}