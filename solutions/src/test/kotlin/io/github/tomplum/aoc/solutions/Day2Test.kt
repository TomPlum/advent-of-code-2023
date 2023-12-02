package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day2Test {
    private val solution = Day2()

    @Test
    fun partOneSolution() {
        assertThat(solution.part1()).isEqualTo(2632)
    }

    @Test
    fun partTwoSolution() {
        assertThat(solution.part2()).isEqualTo(69629)
    }
}