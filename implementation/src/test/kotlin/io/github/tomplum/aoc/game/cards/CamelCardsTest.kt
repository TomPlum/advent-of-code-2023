package io.github.tomplum.aoc.game.cards

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CamelCardsTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("day7/example.txt").value
        val cards = CamelCards(input)
        assertThat(cards.calculateTotalWinnings()).isEqualTo(6440)
    }
}