package io.github.tomplum.aoc.maze

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class PipeMazeTest {
    @Test
    fun exampleSimplePartOne() {
        val input = TestInputReader.read<String>("day10/example-1.txt")
        val pipeMaze = PipeMaze(input.value)
        assertThat(pipeMaze.calculateStepsToFarthestPosition()).isEqualTo(4)
    }

    @Test
    fun exampleComplexPartOne() {
        val input = TestInputReader.read<String>("day10/example-2.txt")
        val pipeMaze = PipeMaze(input.value)
        assertThat(pipeMaze.calculateStepsToFarthestPosition()).isEqualTo(8)
    }

    @Test
    fun exampleThreePartTwo() {
        val input = TestInputReader.read<String>("day10/example-3.txt")
        val pipeMaze = PipeMaze(input.value)
        assertThat(pipeMaze.determinePointsInsidePipeLoop()).isEqualTo(4)
    }
}