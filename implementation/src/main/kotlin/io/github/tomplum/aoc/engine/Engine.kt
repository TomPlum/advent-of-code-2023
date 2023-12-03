package io.github.tomplum.aoc.engine

import io.github.tomplum.libs.extensions.product
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

class Engine(private val schematic: List<String>) : AdventMap2D<EnginePart>() {

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

    /**
     * Determines the part number of the engine from
     * the values in the [schematic].
     *
     * A part number is a numerical value in an [EnginePart]
     * that is adjacent to a symbol.
     *
     * @return The sum of the part numbers.
     */
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

    /**
     * Determines the ratio of every gear in the engine.
     * A gear is denoted by a '*' symbol in the [schematic]
     * that is adjacent to exactly two part numbers.
     *
     * @return The sum of the gear ratios.
     */
    fun determineGearRatio(): Long {
        val numbers = groups.map { group ->
            group.map { (_, part) -> part.value.toString().toInt() }
                .joinToString("")
                .toLong()
                .let { partNumber -> Pair(group.map { (pos) -> pos }, partNumber) }
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