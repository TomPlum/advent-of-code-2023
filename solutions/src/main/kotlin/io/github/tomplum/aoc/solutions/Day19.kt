package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.machine.MachinePartsSorter
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day19: Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(19)).value
    private val machinePartsSorter = MachinePartsSorter(data)

    override fun part1(): Int {
        return machinePartsSorter.sort()
    }
}