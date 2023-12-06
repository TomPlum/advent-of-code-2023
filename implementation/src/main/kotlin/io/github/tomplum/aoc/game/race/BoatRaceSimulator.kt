package io.github.tomplum.aoc.game.race

import io.github.tomplum.aoc.game.race.strategy.RaceSimulationStrategy

data class Race(val duration: Int, val recordDistance: Int)

class BoatRaceSimulator {
    fun calculateWinningMethods(strategy: RaceSimulationStrategy): Long {
        return strategy.calculateWinningMethodQuantityProduct()
    }
}