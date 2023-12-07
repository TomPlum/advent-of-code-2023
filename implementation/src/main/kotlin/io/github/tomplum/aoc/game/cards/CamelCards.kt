package io.github.tomplum.aoc.game.cards

import kotlin.time.times

class CamelCards(cardsData: List<String>) {

    private val hands = cardsData.associate { line ->
        val (hands, bid) = line.trim().split(" ")
        hands to bid
    }

    private val suits = listOf('A', 'K', 'Q', 'J', 'T') + (9 downTo 2).map { number -> number.toString()[0] }

    fun calculateTotalWinnings(): Int {
        val ordered = hands.entries.fold(mutableMapOf<HandType, List<String>>()) { acc, (hand, bid) ->
            val list = acc.computeIfAbsent(hand.parseHandType()) { _ -> mutableListOf() }.toMutableList()
            list.add(hand)
            acc[hand.parseHandType()] = list
            acc
        }

        return ordered.map {
            if (it.value.size > 1) {
                val sorted = it.value.sortedWith { o1, o2 ->
                    val (stronger) = Pair(o1, o2).compareHands()
                    if (o1 == stronger) {
                        -1
                    } else if (o2 == stronger) {
                        1
                    } else {
                        0
                    }
                }
                Pair(it.key, sorted)
            } else {
                it.toPair()
            }
        }.toMap().entries
            .sortedByDescending { it.key.strength }
            .flatMap { it.value }
            .reversed()
            .mapIndexed { i, hand -> (i + 1) to hand }
            .sumOf { (rank, hand) -> rank * hands[hand]!!.toInt() }
    }

    private fun String.parseHandType(): HandType {
        if (this.all { card -> card == this[0] }) {
            return HandType.FIVE_OF_A_KIND
        } else if (this.isNofKind(4)) {
            return HandType.FOUR_OF_A_KIND
        } else if (this.isNofKind(3)) {
            return HandType.THREE_OF_A_KIND
        } else if (this.isNofKind(3) && this.isNofKind(2)) {
            return HandType.FULL_HOUSE
        } else if (this.groupBy { it }.count { it.value.size == 2 } == 2) {
            return HandType.TWO_PAIR
        } else if (this.groupBy { it }.count { it.value.size == 2 } == 1) {
            return HandType.ONE_PAIR
        } else if (this.toList().distinct().size == this.length) {
            return HandType.HIGH_CARD
        } else {
            return HandType.NO_HAND
        }
    }

    private fun String.isNofKind(n: Int) = this.groupBy { it }.any { it.value.size == n }

    private fun Pair<String, String>.compareHands(): List<String> {
        (0..<this.first.length).forEach { i ->
            if (this.first[i] != this.second[i]) {
                val firstStrength = suits.reversed().indexOf(this.first[i])
                val secondStrength = suits.reversed().indexOf(this.second[i])

                return if (firstStrength > secondStrength) {
                    listOf(this.first, this.second)
                } else {
                    listOf(this.second, this.first)
                }
            }
        }

        return listOf(this.first, this.second)
    }
}