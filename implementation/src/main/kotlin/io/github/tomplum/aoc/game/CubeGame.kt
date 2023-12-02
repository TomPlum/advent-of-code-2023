package io.github.tomplum.aoc.game

enum class Colour {
    RED, GREEN, BLUE
}

data class Game(val id: Int, val subsets: List<Map<Colour, Int>>)

class CubeGame(record: List<String>) {

    private val games = record.map { game ->
        val regex = Regex("Game (?<id>\\d+): (?<sets>.*)")
        val match = regex.find(game)!!

        val id = match.groups["id"]?.value?.toInt() ?: 0

        val subsets = match.groups["sets"]!!.value.split(";").map { segment ->
            val segmentRegex = Regex("(?<quantity>\\d+) (?<colour>red|green|blue)")
            segment.split(",").fold(mutableMapOf<Colour, Int>()) { acc, value ->
                val segmentMatch = segmentRegex.find(value.trim())!!
                val quantity = segmentMatch.groups["quantity"]!!.value.toInt()
                val colour = segmentMatch.groups["colour"]!!.value
                acc[Colour.valueOf(colour.uppercase())] = quantity
                acc
            }
        }

        Game(id, subsets)
    }

    fun determinePossibleGames(red: Int, green: Int, blue: Int): List<Int> = games
        .filter { game ->
            game.subsets.all { subset ->
                (subset[Colour.BLUE] ?: 0) <= blue &&
                (subset[Colour.RED] ?: 0) <= red &&
                (subset[Colour.GREEN] ?: 0) <= green
            }
        }
        .map { game -> game.id }

    fun determinePower(): Int = games.sumOf { game ->
        val blue = game.subsets.maxOf { subset -> subset[Colour.BLUE] ?: 0 }
        val red = game.subsets.maxOf { subset -> subset[Colour.RED] ?: 0 }
        val green = game.subsets.maxOf { subset -> subset[Colour.GREEN] ?: 0 }
        blue * red * green
    }
}