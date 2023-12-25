package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day25Test {
    private val solution = Day25()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(555702)
    }
}