package io.github.tomplum.aoc.game.scratchcard

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ScratchCardAnalyserTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("day4/example.txt").value
        val analyser = ScratchCardAnalyser(input)
        assertThat(analyser.calculateTotalPoints()).isEqualTo(13)
    }
}