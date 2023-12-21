package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.garden.GardenMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day21: Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(21)).value
    private val gardenMap = GardenMap(data)

    override fun part1(): Int {
        return gardenMap.traverse(64)
    }
}