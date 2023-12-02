package io.github.tomplum.aoc.game

data class Cubes(val red: Int, val green: Int, val blue: Int)

data class Game(val id: Int, val subsets: List<Cubes>)

class SnowGame(val record: List<String>) {

    private val games = record.map { game ->
        val parts = game.split(": ")
        val id = parts[0].split(" ")[1].toInt()
        val subsets = parts[1].split(";").map { segment ->
            var blue = 0
            var red = 0
            var green = 0

            segment.trim().split(",").forEach { info ->
                val bits = info.trim().split(" ")
                val quantity = bits[0].toInt()
                when(bits[1]) {
                    "blue" -> {
                        blue = quantity
                    }
                    "red" -> {
                        red = quantity
                    }
                    "green" -> {
                        green = quantity
                    }
                    else -> {
                        throw IllegalArgumentException("Invalid colour ${bits[1]}")
                    }
                }
            }

            val info = Cubes(red, green, blue)
            blue = 0
            red = 0
            green = 0
            info
        }

        Game(id, subsets)
    }

    fun determinePossibleGames(red: Int, green: Int, blue: Int): List<Int> {
        val possibleGames = games.filter { game ->
            game.subsets.all { subset ->
                subset.blue <= blue && subset.red <= red && subset.green <= green
            }
        }

        return possibleGames.map { game -> game.id }
    }

    fun determinePower(): Int = games.sumOf { game ->
        val blue = game.subsets.maxOf { subset -> subset.blue }
        val red = game.subsets.maxOf { subset -> subset.red }
        val green = game.subsets.maxOf { subset -> subset.green }
        blue * red * green
    }
}