package io.github.tomplum.aoc.game.cards

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CamelCardsTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("day7/example.txt").value
        val cards = CamelCards(input)
        assertThat(cards.calculateTotalWinnings()).isEqualTo(6440)
    }

    @Test
    fun examplePartTwo() {
        val input = TestInputReader.read<String>("day7/example.txt").value
        val cards = CamelCards(input)
        assertThat(cards.calculateTotalWinningsWithJoker()).isEqualTo(5905)
    }

    @Nested
    inner class ParseHandType {
        private val cards = CamelCards(listOf())

        @ParameterizedTest
        @ValueSource(strings = ["AAAAA", "KKKKK", "22222"])
        fun fiveOfAKind(hand: String) {
            assertThat(cards.parseHandType(hand)).isEqualTo(HandType.FIVE_OF_A_KIND)
        }

        @ParameterizedTest
        @ValueSource(strings = ["AAAAK", "KKKKA", "22221"])
        fun fourOfAKind(hand: String) {
            assertThat(cards.parseHandType(hand)).isEqualTo(HandType.FOUR_OF_A_KIND)
        }

        @ParameterizedTest
        @ValueSource(strings = ["AAAKK", "KKKAA", "22211"])
        fun fullHouse(hand: String) {
            assertThat(cards.parseHandType(hand)).isEqualTo(HandType.FULL_HOUSE)
        }
    }
}