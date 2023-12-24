package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.weather.hail.HailstoneSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day24: Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(24)).value
    private val hailstoneSimulator = HailstoneSimulator(data)

    override fun part1(): Int {
        return hailstoneSimulator.simulate(xTestArea = 200000000000000, yTestArea = 400000000000000)
    }
}