package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.CosmicMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day11 : Solution<Long, Long> {
    private val data = InputReader.read<String>(Day(11)).value
    private val cosmicMap = CosmicMap(data)

    override fun part1(): Long {
        return cosmicMap.calculateGalacticDistancesAfterExpansion()
    }

    override fun part2(): Long {
        return cosmicMap.calculateGalacticDistancesAfterExpansion(horizontalExpansion = 1000000, verticalExpansion = 1000000)
    }
}