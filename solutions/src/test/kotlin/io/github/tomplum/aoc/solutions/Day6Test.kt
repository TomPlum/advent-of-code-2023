package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day6Test {
    private val solution = Day6()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(1413720)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(30565288)
    }
}