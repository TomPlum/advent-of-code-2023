package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.light.Contraption
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day16: Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(16)).value
    private val contraption = Contraption(data)

    override fun part1(): Int {
        return contraption.countEnergisedTiles()
    }
}