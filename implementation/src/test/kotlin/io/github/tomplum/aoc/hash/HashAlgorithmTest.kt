package io.github.tomplum.aoc.hash

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class HashAlgorithmTest {
    private val input = TestInputReader.read<String>("day15/example.txt").asSingleString()
    private val hashAlgorithm = HashAlgorithm(input)

    @Test
    fun partOne() {
        assertThat(hashAlgorithm.run()).isEqualTo(1320)
    }
}