package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.SnowGame
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day2 : Solution<Int, Int> {

    private val record = InputReader.read<String>(Day(2)).value
    private val snowGame = SnowGame(record)

    override fun part1(): Int {
        return snowGame.determinePossibleGames(red = 12, green = 13, blue = 14).sum()
    }
}