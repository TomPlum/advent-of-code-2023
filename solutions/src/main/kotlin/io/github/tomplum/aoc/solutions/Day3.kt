package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.Engine
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day3 : Solution<Int, Int> {

    private val schematic = InputReader.read<String>(Day(3)).value
    private val engine = Engine(schematic)

    override fun part1(): Int {
        return engine.determinePartNumbers()
    }
}