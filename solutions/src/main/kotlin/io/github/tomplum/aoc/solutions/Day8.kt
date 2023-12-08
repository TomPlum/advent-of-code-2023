package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.network.DesertNetwork
import io.github.tomplum.aoc.network.strategy.EtherealNavigation
import io.github.tomplum.aoc.network.strategy.LeftRightNavigation
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day8 : Solution<Long, Long> {
    private val documents = InputReader.read<String>(Day(8)).value
    private val network = DesertNetwork()

    override fun part1(): Long {
        val strategy = LeftRightNavigation(documents)
        return network.stepsRequiredToReachEnd(strategy)
    }

    override fun part2(): Long {
        val strategy = EtherealNavigation(documents)
        return network.stepsRequiredToReachEnd(strategy)
    }
}