package io.github.tomplum.aoc.map.light

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ContraptionTest {
    private val data = TestInputReader.read<String>("day16/example.txt").value
    private val contraption = Contraption(data)

    @Test
    fun examplePartOne() {
        assertThat(contraption.countEnergisedTiles()).isEqualTo(46)
    }

    @Test
    fun examplePartTwo() {
        assertThat(contraption.countEnergisedTilesFromAllEdgeEntryPoints()).isEqualTo(51)
    }
}