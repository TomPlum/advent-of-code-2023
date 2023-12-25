package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.machine.wire.ComponentWiring
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day25: Solution<Int, Int> {
    private val wiringDiagram = InputReader.read<String>(Day(25)).value
    private val componentWiring = ComponentWiring(wiringDiagram)

    override fun part1(): Int {
        return componentWiring.disconnectWires()
    }
}