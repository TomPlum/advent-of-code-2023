package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day21Test {
    private val solution = Day21()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(3677)
    }
}