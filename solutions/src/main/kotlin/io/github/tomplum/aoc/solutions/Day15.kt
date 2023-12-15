package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.hash.HashAlgorithm
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day15: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(15)).asSingleString()
    private val algorithm = HashAlgorithm(input)

    override fun part1(): Int {
        return algorithm.run()
    }

    override fun part2(): Int {
        return algorithm.calculateFocusingPower()
    }
}