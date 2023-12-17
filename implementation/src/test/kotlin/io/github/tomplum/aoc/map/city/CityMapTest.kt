package io.github.tomplum.aoc.map.city

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CityMapTest {

    @Test
    fun examplePartOne() {
        val data = TestInputReader.read<String>("day17/example.txt")
        val citMap = CityMap(data.value)
        assertThat(citMap.traverseWithCrucible()).isEqualTo(102)
    }

    @Test
    fun exampleOnePartTwo() {
        val data = TestInputReader.read<String>("day17/example.txt")
        val citMap = CityMap(data.value)
        assertThat(citMap.traverseWithUltraCrucible()).isEqualTo(94)
    }

    @Test
    fun exampleTwoPartTwo() {
        val data = TestInputReader.read<String>("day17/example-2.txt")
        val citMap = CityMap(data.value)
        assertThat(citMap.traverseWithUltraCrucible()).isEqualTo(71)
    }
}