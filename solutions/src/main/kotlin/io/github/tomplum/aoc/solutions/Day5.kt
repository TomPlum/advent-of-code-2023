package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.Almanac
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day5 : Solution<Long, Long> {
    private val input = InputReader.read<String>(Day(5)).value
    private val almanac = Almanac(input)

    override fun part1(): Long {
        return almanac.findLowestLocationNumber()
    }
}