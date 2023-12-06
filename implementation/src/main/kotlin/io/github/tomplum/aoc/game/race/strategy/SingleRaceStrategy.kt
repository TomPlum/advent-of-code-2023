package io.github.tomplum.aoc.game.race.strategy

class SingleRaceStrategy(document: List<String>) : RaceSimulationStrategy {
    private val duration = document.first()
        .removePrefix("Time:")
        .dropWhile { char -> char == ' ' }
        .replace(" ", "")
        .trim()
        .toLong()

    private val recordDistance = document.last()
        .removePrefix("Distance:")
        .dropWhile { char -> char == ' ' }
        .replace(" ", "")
        .trim()
        .toLong()

    override fun calculateWinningMethodQuantityProduct(): Long = (0..duration).map { chargingTime ->
        val timeLeftToMove = duration - chargingTime
        chargingTime * timeLeftToMove
    }.count { distance -> distance > recordDistance }.toLong()
}