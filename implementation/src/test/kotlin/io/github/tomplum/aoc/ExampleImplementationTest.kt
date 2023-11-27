package io.github.tomplum.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class ExampleImplementationTest {

    private val implementation = ExampleImplementation()

    @Test
    fun shouldReturnTheSolution() {
        val solution = implementation.solveSomeProblem()
        assertThat(solution).isEqualTo(24) }
}