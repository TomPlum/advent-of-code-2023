package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.network.DesertNetwork
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day8 : Solution<Int, Int> {
    private val documents = InputReader.read<String>(Day(8)).value
    private val network = DesertNetwork(documents)

    override fun part1(): Int {
        return network.stepsRequiredToReachEnd()
    }
}