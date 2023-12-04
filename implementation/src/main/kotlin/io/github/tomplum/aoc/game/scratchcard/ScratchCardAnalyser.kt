package io.github.tomplum.aoc.game.scratchcard

class ScratchCardAnalyser(table: List<String>) {

    private val scratchCards = table.map { data ->
        val split = data.split("|")
        val left = split[0]
        val leftParts = left.split(": ")
        val winning = leftParts[1].trim().split(" ").filter { value -> value.isNotBlank() }.map { value -> value.toInt() }
        val id = leftParts[0].removePrefix("Card ").trim().toInt()
        val numbers = split[1].trim().split(" ").filter { value -> value.isNotBlank() }.map { value -> value.toInt() }
        ScratchCard(id, winning, numbers)
    }

    private val cardMatches = scratchCards.associate { card ->
        card.id to card.winningNumbers.count { winning -> winning in card.numbers }
    }

    fun calculateTotalPoints(): Int = scratchCards.fold(0) { points, card ->
        var cardPoints = 0

        card.winningNumbers.forEach { winning ->
            if (winning in card.numbers) {
                if (cardPoints == 0) cardPoints += 1 else cardPoints *= 2
            }
        }

        points + cardPoints
    }

    fun calculateTotalScratchCardQuantity() = cardMatches.keys
        .sumOf { id -> extrapolateCardGeneration(id) }
        .let { extrapolated -> extrapolated + cardMatches.keys.size }

    private fun extrapolateCardGeneration(id: Int): Int {
        val matches = cardMatches[id]!!
        val cardsToCopy = ((id + 1)..(matches + id)).toList()
        var quantityCopied = cardsToCopy.size

        quantityCopied += cardsToCopy
            .filter { idCopied -> cardMatches.containsKey(idCopied) }
            .sumOf { idCopied -> extrapolateCardGeneration(idCopied) }

        return quantityCopied
    }
}