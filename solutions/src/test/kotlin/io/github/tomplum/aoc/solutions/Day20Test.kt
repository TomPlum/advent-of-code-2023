package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day20Test {
    private val solution = Day20()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(684125385)
    }
}