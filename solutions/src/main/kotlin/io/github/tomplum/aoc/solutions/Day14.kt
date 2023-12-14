package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.platform.PlatformMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day14 : Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(14)).value
    private val platformMap = PlatformMap(data)

    override fun part1(): Int {
        return platformMap.calculateNorthSupportBeamsTotalLoad()
    }

    override fun part2(): Int {
        return platformMap.calculateNorthSupportBeamsTotalLoad(cycles = 1000000000)
    }
}