package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.onsen.SpringAnalyser
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day12 : Solution<Long, Long> {
    private val records = InputReader.read<String>(Day(12)).value
    private val springAnalyser = SpringAnalyser(records)

    override fun part1(): Long {
        return springAnalyser.calculateArrangements()
    }

    override fun part2(): Long {
        return springAnalyser.calculateArrangements(copies = 5)
    }
}