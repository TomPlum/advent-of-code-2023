package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day1Test {

    private val solution = Day1()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(55712)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(55413)
    }
}
