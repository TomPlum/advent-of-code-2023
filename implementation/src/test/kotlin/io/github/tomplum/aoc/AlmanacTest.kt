package io.github.tomplum.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class AlmanacTest {
    private val input = TestInputReader.read<String>("day5/example.txt").value

    @Test
    fun examplePartOne() {
        val almanac = Almanac(input)
        val lowestLocationNumber = almanac.findLowestLocationNumber()
        assertThat(lowestLocationNumber).isEqualTo(35)
    }

    @Test
    fun examplePartTwo() {
        val almanac = Almanac(input)
        val lowestLocationNumber = almanac.findLowestLocationNumberSeedRange()
        assertThat(lowestLocationNumber).isEqualTo(46)
    }
}