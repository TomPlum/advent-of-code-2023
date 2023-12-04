package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.scratchcard.ScratchCardAnalyser
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day4 : Solution<Int, Int> {

    private val dataData = InputReader.read<String>(Day(4)).value
    private val analyser = ScratchCardAnalyser(dataData)

    override fun part1(): Int {
        return analyser.calculateTotalPoints()
    }

    override fun part2(): Int {
        return analyser.calculateTotalScratchCardQuantity()
    }
}