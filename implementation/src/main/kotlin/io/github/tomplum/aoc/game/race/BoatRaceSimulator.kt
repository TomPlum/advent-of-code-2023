package io.github.tomplum.aoc.game.race

import io.github.tomplum.aoc.game.race.strategy.RaceSimulationStrategy

class BoatRaceSimulator {
    fun calculateWinningMethods(strategy: RaceSimulationStrategy): Long {
        return strategy.calculateWinningMethodQuantityProduct()
    }
}