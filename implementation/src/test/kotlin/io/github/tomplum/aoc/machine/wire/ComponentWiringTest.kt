package io.github.tomplum.aoc.machine.wire

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ComponentWiringTest {
    private val data = TestInputReader.read<String>("day25/example.txt").value
    private val componentWiring = ComponentWiring(data)

    @Test
    fun partOneExample() {
        assertThat(componentWiring.disconnectWires()).isEqualTo(54)
    }
}