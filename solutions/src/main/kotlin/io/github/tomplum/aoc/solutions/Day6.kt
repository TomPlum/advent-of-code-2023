package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.race.BoatRaceSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day6 : Solution<Int, Long> {

    private val document = InputReader.read<String>(Day(6)).value
    private val boatRaceSimulator = BoatRaceSimulator(document)

    override fun part1(): Int {
        return boatRaceSimulator.calculateWinningMethodQuantityProduct()
    }

    override fun part2(): Long {
        return boatRaceSimulator.calculateWinningMethodQuantityProductNewStrategy()
    }
}