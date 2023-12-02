package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.CubeGame
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day2 : Solution<Int, Int> {

    private val record = InputReader.read<String>(Day(2)).value
    private val cubeGame = CubeGame(record)

    override fun part1(): Int {
        return cubeGame.determinePossibleGames(red = 12, green = 13, blue = 14).sum()
    }

    override fun part2(): Int {
        return cubeGame.determinePower()
    }
}