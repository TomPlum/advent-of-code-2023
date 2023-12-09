package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.sensor.Oasis
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day9 : Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(9)).value
    private val oasis = Oasis(input)

    override fun part1(): Int {
        return oasis.extrapolateFromHistory()
    }
}