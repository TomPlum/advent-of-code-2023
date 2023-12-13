package io.github.tomplum.aoc.map.lava

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class LavaPatternAnalyserTest {
    private val data = TestInputReader.read<String>("day13/example.txt").value
    private val lavaPatternAnalyser = LavaPatternAnalyser(data)

    @Test
    fun examplePartOne() {
        assertThat(lavaPatternAnalyser.summariseNotes()).isEqualTo(405)
    }
}