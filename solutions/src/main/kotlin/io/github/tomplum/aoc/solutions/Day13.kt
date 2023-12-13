package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.lava.LavaPatternAnalyser
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day13 : Solution<Int, Int> {
    private val notes = InputReader.read<String>(Day(13))
    private val patternAnalyser = LavaPatternAnalyser(notes.value)

    override fun part1(): Int {
        return patternAnalyser.summariseNotes()
    }
}