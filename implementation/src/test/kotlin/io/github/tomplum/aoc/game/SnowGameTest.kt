package io.github.tomplum.aoc.game

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class SnowGameTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("day2/example-1.txt").value
        val game = SnowGame(input)
        val ids = game.determinePossibleGames(12, 13, 14)
        assertThat(ids.sum()).isEqualTo(8)
    }

    @Test
    fun examplePartTwo() {
        val input = TestInputReader.read<String>("day2/example-1.txt").value
        val game = SnowGame(input)
        val power = game.determinePower()
        assertThat(power).isEqualTo(2286)
    }
}