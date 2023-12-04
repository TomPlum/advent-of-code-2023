package io.github.tomplum.aoc.game.scratchcard

class ScratchCardAnalyser(table: List<String>) {

    val scratchCards = table.map { data ->
        val split = data.split("|")
        val left = split[0]
        val leftParts = left.split(": ")
        val winning = leftParts[1].trim().split(" ").filter { value -> value.isNotBlank() }.map { value -> value.toInt() }
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

    val cardMatches = scratchCards.associate { card ->
        val matches = card.winningNumbers.count { winning -> winning in card.numbers }
        card.id to matches
    }

    fun calculateTotalScratchCardQuantity(): Int {


        val sum = cardMatches.map { (id, matches) -> extrapolateCardGeneration(id, matches) }.sum()

        return sum + cardMatches.keys.size
    }

    private fun extrapolateCardGeneration(id: Int, matches: Int): Int {
        val cardsToCopy = (id + 1)..(matches + id)
        var copied = cardsToCopy.toList().size

        copied += cardsToCopy
            .filter { idCopied -> cardMatches.containsKey(idCopied) }
            .sumOf { idCopied ->
                extrapolateCardGeneration(idCopied, cardMatches[idCopied]!!)
            }

        return copied
    }
}