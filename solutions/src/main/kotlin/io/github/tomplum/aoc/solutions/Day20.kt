package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.module.CableNetwork
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day20: Solution<Long, Long> {
    private val config = InputReader.read<String>(Day(20)).value
    private val cableNetwork = CableNetwork(config)

    override fun part1(): Long {
        return cableNetwork.getPulseCount()
    }
}