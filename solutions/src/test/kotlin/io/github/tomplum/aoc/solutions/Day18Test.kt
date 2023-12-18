package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day18Test {
    private val solution = Day18()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(26857)
    }
}