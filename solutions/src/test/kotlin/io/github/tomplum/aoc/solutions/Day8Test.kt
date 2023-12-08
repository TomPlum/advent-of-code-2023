package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day8Test {
    private val solution = Day8()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(12169)
    }
}