package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day7Test {
    private val solution = Day7()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(250232501)
    }
}