package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.module.SandMachineModuleNetwork
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day20: Solution<Long, Long> {
    private val config = InputReader.read<String>(Day(20)).value
    private val cableNetwork = SandMachineModuleNetwork(config)

    override fun part1(): Long {
        return cableNetwork.getPulseCount()
    }

    override fun part2(): Long {
        return cableNetwork.getButtonPressesRequiredToDeliverToModule("rx")
    }
}