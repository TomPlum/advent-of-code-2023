package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day13Test {
    private val solution = Day13()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(33122)
    }
}