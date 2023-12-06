package io.github.tomplum.aoc.game.race

import io.github.tomplum.libs.extensions.product

data class Race(val duration: Int, val recordDistance: Int)

class BoatRaceSimulator(document: List<String>) {

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
        Race(time, distances[iTime])
    }

    fun calculateWinningMethodQuantityProduct(): Int = races.map { (duration, recordDistance) ->
        (0..duration).map { timeSpentChargingSpeed ->
            val timeLeftToMove = duration - timeSpentChargingSpeed
            timeSpentChargingSpeed * timeLeftToMove
        }.count { distance -> distance > recordDistance }
    }.product()

    private val singleRaceTime = document.first()
        .removePrefix("Time:")
        .dropWhile { char -> char == ' ' }
        .replace(" ", "")
        .trim()
        .toLong()

    private val singleRaceDistance = document.last()
        .removePrefix("Distance:")
        .dropWhile { char -> char == ' ' }
        .replace(" ", "")
        .trim()
        .toLong()

    // TODO: Rename stuff
    fun calculateWinningMethodQuantityProductNewStrategy(): Long = (0..singleRaceTime).map { timeSpentChargingSpeed ->
        val timeLeftToMove = singleRaceTime - timeSpentChargingSpeed
        timeSpentChargingSpeed * timeLeftToMove
    }.count { distance -> distance > singleRaceDistance }.toLong()
}