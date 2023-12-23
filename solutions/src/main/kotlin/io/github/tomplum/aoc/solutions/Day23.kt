package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.forest.ForestMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day23: Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(23)).value
    private val forestMap = ForestMap(data)

    override fun part1(): Int {
        return forestMap.longestHikeSteps()
    }

    override fun part2(): Int {
        return forestMap.longestHikeStepsNoSlopes()
    }
}