package io.github.tomplum.aoc.game.cards

class CamelCards(cardsData: List<String>) {

    private val hands = cardsData.associate { line ->
        val (hands, bid) = line.trim().split(" ")
        hands to bid
    }

    private val suits = listOf('A', 'K', 'Q', 'J', 'T') + (9 downTo 2).map { number -> number.toString()[0] }
    private val suitsWithJoker = listOf('A', 'K', 'Q', 'T') + (9 downTo 2).map { number -> number.toString()[0] } + 'J'

    fun calculateTotalWinnings(): Int {
        val ordered = hands.entries.fold(mutableMapOf<HandType, List<String>>()) { acc, (hand, bid) ->
            val handType = parseHandType(hand)
            val list = acc.computeIfAbsent(handType) { _ -> mutableListOf() }.toMutableList()
            list.add(hand)
            acc[handType] = list
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

    fun calculateTotalWinningsWithJoker(): Int {
        val ordered = hands.entries.fold(mutableMapOf<HandType, List<String>>()) { acc, (hand, bid) ->
            val handType = if (hand.any { it == 'J' }) hand.determineBestJokerHand() else parseHandType(hand)
            val list = acc.computeIfAbsent(handType) { _ -> mutableListOf() }.toMutableList()
            list.add(hand)
            acc[handType] = list
            acc
        }

        return ordered.map {
            if (it.value.size > 1) {
                val sorted = it.value.sortedWith { o1, o2 ->
                    val (stronger) = Pair(o1, o2).compareHandsWithJoker()
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
        }
        .toMap()
        .entries
        .sortedByDescending { it.key.strength }
        .flatMap { it.value }
        .reversed()
        .mapIndexed { i, hand -> (i + 1) to hand }
        .sumOf { (rank, hand) -> rank * hands[hand]!!.toInt() }
    }

    fun parseHandType(hand: String): HandType {
        if (hand.all { card -> card == hand[0] }) {
            return HandType.FIVE_OF_A_KIND
        } else if (hand.isNofKind(4)) {
            return HandType.FOUR_OF_A_KIND
        } else if (hand.groupBy { it }.filter { it.value.size > 1 }.size == 1 && hand.groupBy { it }.values.size == 3) {
            return HandType.THREE_OF_A_KIND
        } else if (hand.isNofKind(3) && hand.isNofKind(2)) {
            return HandType.FULL_HOUSE
        } else if (hand.groupBy { it }.count { it.value.size == 2 } == 2) {
            return HandType.TWO_PAIR
        } else if (hand.groupBy { it }.count { it.value.size == 2 } == 1) {
            return HandType.ONE_PAIR
        } else if (hand.toList().distinct().size == hand.length) {
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

    private fun Pair<String, String>.compareHandsWithJoker(): List<String> {
        (0..<this.first.length).forEach { i ->
            if (this.first[i] != this.second[i]) {
                val firstStrength = suitsWithJoker.reversed().indexOf(this.first[i])
                val secondStrength = suitsWithJoker.reversed().indexOf(this.second[i])

                return if (firstStrength > secondStrength) {
                    listOf(this.first, this.second)
                } else {
                    listOf(this.second, this.first)
                }
            }
        }

        return listOf(this.first, this.second)
    }

    private fun jokerHands(hands: List<String>): List<String> {
        return hands.flatMap { hand ->
            if (hand.any { card -> card == 'J' }) {
                val permutations = suitsWithJoker.dropLast(1).map { suit ->
                    hand.replaceFirst('J', suit)
                }
                jokerHands(permutations)
            } else {
                hands
            }
        }
    }

    private fun String.determineBestJokerHand(): HandType {
        val indexes = this
            .mapIndexedNotNull { i, card -> if (card == 'J') i else null  }

        val jokerHands = jokerHands(listOf(this))

      /*  val s = this
            .mapIndexedNotNull { i, card -> if (card == 'J') i else null  }
            .flatMap { jokerIndex ->
                val hands = suitsWithJoker.map { suit ->
                    this.replaceRange(jokerIndex, jokerIndex + 1, suit.toString())
                }

                indexes.flatMap { nestedIndex ->
                    suitsWithJoker.map { suit ->
                        this.replaceRange(jokerIndex, jokerIndex + 1, suit.toString())
                    }
                }
            }
            .map { hand -> parseHandType(hand) }
            .maxBy { handType -> handType.strength }*/

        return jokerHands.map { hand -> parseHandType(hand) }
            .maxBy { handType -> handType.strength }
    }
}