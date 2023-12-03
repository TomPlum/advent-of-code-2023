package io.github.tomplum.aoc

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class Engine(schematic: List<String>) : AdventMap2D<EnginePart>() {

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
    }

    fun determinePartNumbers(): Int {
        val groups = mutableListOf<List<Pair<Point2D, EnginePart>>>()
        var index = 0
        var group = mutableListOf<Pair<Point2D, EnginePart>>()
        getDataMap().entries.forEach { (pos, part) ->
            if (part.isIntegerValue()) {
                group.add(Pair(pos, part))
            } else {
                groups.add(group)
                group = mutableListOf()
                index++
            }
        }
        val numbers = groups
            .asSequence()
            .filter { it.isNotEmpty() }
            .filter {
                val pos = it.map { (pos, _) -> pos }
                    .flatMap { pos -> pos.adjacent() }
                pos.any { position -> getTile(position, EnginePart('.')).isSymbol() }
            }
            .map { it.map { it.second.value.toString() } }
            .map { it.joinToString("") }
            .map { it.toInt() }
            .toList()

        /*filterPoints(getDataMap().keys) { pos ->
            adjacentTilesOrthogonal(pos).any { adj ->
                adj.value.isSymbol()
            }
        }*/

        return numbers.sum()
    }
}