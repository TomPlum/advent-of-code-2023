package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.cards.CamelCards
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day7 : Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(7)).value
    private val camelCards = CamelCards(input)

    override fun part1(): Int {
        return camelCards.calculateTotalWinnings()
    }
}