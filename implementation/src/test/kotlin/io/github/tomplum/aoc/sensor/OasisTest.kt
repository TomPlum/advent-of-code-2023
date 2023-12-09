package io.github.tomplum.aoc.sensor

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class OasisTest {
    private val input = TestInputReader.read<String>("day9/example.txt")
    private val oasis = Oasis(input.value)

    @Test
    fun examplePartOne() {
        assertThat(oasis.extrapolateFromHistory()).isEqualTo(114)
    }

    @Test
    fun examplePartTwo() {
        assertThat(oasis.extrapolateBackwards()).isEqualTo(2)
    }
}