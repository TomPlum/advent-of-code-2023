package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day17Test {
    private val solution = Day17()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(698)
    }
}