package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.race.BoatRaceSimulator
import io.github.tomplum.aoc.game.race.strategy.MultipleRaceStrategy
import io.github.tomplum.aoc.game.race.strategy.SingleRaceStrategy
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day6 : Solution<Long, Long> {

    private val document = InputReader.read<String>(Day(6)).value
    private val boatRaceSimulator = BoatRaceSimulator()

    override fun part1(): Long {
        val strategy = MultipleRaceStrategy(document)
        return boatRaceSimulator.calculateWinningMethods(strategy)
    }

    override fun part2(): Long {
        val strategy = SingleRaceStrategy(document)
        return boatRaceSimulator.calculateWinningMethods(strategy)
    }
}