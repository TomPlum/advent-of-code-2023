package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day24Test {
    private val solution = Day24()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(31208)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(580043851566574)
    }
}