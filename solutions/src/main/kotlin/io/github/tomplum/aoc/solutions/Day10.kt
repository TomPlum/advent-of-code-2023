package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.maze.PipeMaze
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day10 : Solution<Int, Int> {
    private val mapData = InputReader.read<String>(Day(10)).value
    private val pipeMaze = PipeMaze(mapData)

    override fun part1(): Int {
        return pipeMaze.calculateStepsToFarthestPosition()
    }

    override fun part2(): Int {
        return pipeMaze.determinePointsInsidePipeLoop()
    }
}