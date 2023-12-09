package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day9Test {
    private val solution = Day9()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(2098530125)
    }
}