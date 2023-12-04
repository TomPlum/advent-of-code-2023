package io.github.tomplum.aoc.game.scratchcard

class ScratchCardAnalyser(table: List<String>) {

    val scratchCards = table.map { data ->
        val split = data.split("|")
        val left = split[0]
        val leftParts = left.split(": ")
        val winning = leftParts[1].trim().split(" ").map { value -> value.toInt() }
        val id = leftParts[0].removePrefix("Card ").trim().toInt()
        val numbers = split[1].trim().split(" ").filter { value -> value.isNotBlank() }.map { value -> value.toInt() }
        ScratchCard(id, winning, numbers)
    }

    fun calculateTotalPoints(): Int = scratchCards.fold(0) { points, card ->
        var cardPoints = 0
        var isFirstMatch = true

        card.winningNumbers.forEach { winning ->
            if (winning in card.numbers) {
                if (isFirstMatch) {
                    cardPoints += 1
                    isFirstMatch = false
                } else {
                    cardPoints *= 2
                }
            }
        }

        points + cardPoints
    }
}