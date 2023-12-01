package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.Calibrator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day1 : Solution<Int, Int> {

    private val input = InputReader.read<String>(Day(1)).value
    private val calibrator = Calibrator(input)

    override fun part1(): Int {
        return calibrator.calibrate()
    }

    override fun part2(): Int {
        return calibrator.calibrateWithWords()
    }
}
