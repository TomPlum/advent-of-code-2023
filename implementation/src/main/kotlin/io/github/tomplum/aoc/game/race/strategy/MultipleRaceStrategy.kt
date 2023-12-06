package io.github.tomplum.aoc.game.race.strategy

import io.github.tomplum.aoc.game.race.RaceRecord
import io.github.tomplum.libs.extensions.product

class MultipleRaceStrategy(document: List<String>) : RaceSimulationStrategy {
    private val times = document.first()
        .removePrefix("Time:").dropWhile { char -> char == ' ' }
        .replace("     ", " ").replace("    ", " ").replace("   ", " ").replace("  ", " ")
        .trim().split(" ")
        .map { value -> value.toInt() }

    private val distances = document.last()
        .removePrefix("Distance:").dropWhile { char -> char == ' ' }
        .replace("   ", " ").replace("  ", " ")
        .trim().split(" ")
        .map { value -> value.toInt() }

    private val races = times.mapIndexed { iTime, time ->
        RaceRecord(time, distances[iTime])
    }

    override fun calculateWinningMethodQuantityProduct(): Long = races.map { (duration, recordDistance) ->
        (0..duration).map { timeSpentChargingSpeed ->
            val timeLeftToMove = duration - timeSpentChargingSpeed
            timeSpentChargingSpeed * timeLeftToMove
        }.count { distance -> distance > recordDistance }.toLong()
    }.product()
}