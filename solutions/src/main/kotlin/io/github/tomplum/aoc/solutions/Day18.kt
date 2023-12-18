package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.lagoon.LagoonMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day18: Solution<Long, Long> {
    private val digPlan = InputReader.read<String>(Day(18)).value
    private val lagoonMap = LagoonMap(digPlan)

    override fun part1(): Long {
        return lagoonMap.calculateVolume()
    }
}