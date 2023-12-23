package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.slabs.SandSlabSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day22: Solution<Int, Int> {
    private val snapshot = InputReader.read<String>(Day(22)).value
    private val sandSlabSimulator = SandSlabSimulator(snapshot)

    override fun part1(): Int {
        return sandSlabSimulator.calculateDisintegratableBricks()
    }

    override fun part2(): Int {
        return sandSlabSimulator.simulateChainReaction()
    }
}