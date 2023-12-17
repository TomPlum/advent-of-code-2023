package io.github.tomplum.aoc.map.city

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CityMapTest {
    private val data = TestInputReader.read<String>("day17/example.txt")
    private val citMap = CityMap(data.value)

    @Test
    fun examplePartOne() {
        assertThat(citMap.directCrucibleFromLavaPoolToMachinePartsFactory()).isEqualTo(102)
    }
}