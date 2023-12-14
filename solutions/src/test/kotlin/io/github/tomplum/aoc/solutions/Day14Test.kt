package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day14Test {
    private val solution = Day14()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(110274)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(12314)
    }
}