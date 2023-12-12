package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day12Test {
    private val solution = Day12()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(7350)
    }
}