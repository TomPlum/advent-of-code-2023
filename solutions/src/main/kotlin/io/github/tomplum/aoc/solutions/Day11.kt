package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.CosmicMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day11 : Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(11)).value
    private val cosmicMap = CosmicMap(data)

    override fun part1(): Int {
        return cosmicMap.calculateGalacticDistancesAfterExpansion()
    }
}