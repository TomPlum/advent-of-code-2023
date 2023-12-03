package io.github.tomplum.aoc

import io.github.tomplum.libs.extensions.product
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

class Engine(schematic: List<String>) : AdventMap2D<EnginePart>() {

    private var groups: List<List<Pair<Point2D, EnginePart>>>

    init {
        var x = 0
        var y = 0

        schematic.forEach { row ->
            row.forEach { column ->
                val tile = EnginePart(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }

        groups = findPartNumberGroups()
    }

    fun determinePartNumbers(): Int = groups
        .asSequence()
        .filter {
            val adjacent = it.map { (pos, _) -> pos }.flatMap { pos -> pos.adjacent() }
            adjacent.any { position -> getTile(position, EnginePart('.')).isSymbol() }
        }
        .map { entries -> entries.map { (_, part) -> part.value.toString() } }
        .map { numbers -> numbers.joinToString("") }
        .map { numberString -> numberString.toInt() }
        .toList()
        .sum()

    fun determineGearRatio(): Long {
        val numbers = groups
            .filter { group -> group.isNotEmpty() }
            .map { group ->
                val points = group.map { (pos) -> pos }
                val number = group.map { (_, part) -> part.value.toString().toInt() }
                    .joinToString("")
                    .toLong()

                Pair(points, number)
            }

        return filterTiles { part -> part.isGearCandidate() }
            .keys
            .mapNotNull { gearCandidatePos ->
                val adjacentPartNumbers = mutableMapOf<List<Point2D>, Long>()

                numbers.filterNot { (pos) ->
                    // If the number is more than 1 row away, it can't be adjacent
                    pos.any { (_, y) -> abs(gearCandidatePos.y - y) > 1 }
                }
                .forEach { (points, number) -> gearCandidatePos.adjacent().forEach { adjPoint ->
                    if (!adjacentPartNumbers.containsKey(points) && adjPoint in points) {
                        adjacentPartNumbers[points] = number
                    }
                }}

                if (adjacentPartNumbers.size == 2) {
                    adjacentPartNumbers.values.toList().product()
                } else null
            }
            .sum()
    }

    private fun findPartNumberGroups(): List<List<Pair<Point2D, EnginePart>>> {
        var index = 0
        val groups = mutableListOf<List<Pair<Point2D, EnginePart>>>()
        var currentGroup = mutableListOf<Pair<Point2D, EnginePart>>()

        getDataMap().entries.forEach { (pos, part) ->
            if (part.isIntegerValue()) {
                currentGroup.add(Pair(pos, part))
            } else {
                groups.add(currentGroup)
                currentGroup = mutableListOf()
                index++
            }
        }

        return groups.filter { group -> group.isNotEmpty() }
    }
}