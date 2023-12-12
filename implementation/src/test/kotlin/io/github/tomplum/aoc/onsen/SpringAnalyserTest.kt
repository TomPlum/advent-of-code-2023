package io.github.tomplum.aoc.onsen

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class SpringAnalyserTest {
    private val input = TestInputReader.read<String>("day12/example.txt")
    private val analyser = SpringAnalyser(input.value)

    @Test
    fun examplePartOne() {
        assertThat(analyser.calculateArrangements()).isEqualTo(21)
    }

    @Test
    fun examplePartTwo() {
        assertThat(analyser.calculateArrangements(5)).isEqualTo(525152)
    }
}