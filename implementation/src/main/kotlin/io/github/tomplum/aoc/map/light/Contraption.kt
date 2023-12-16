package io.github.tomplum.aoc.map.light

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class Contraption(data: List<String>): AdventMap2D<ContraptionTile>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = ContraptionTile(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun countEnergisedTiles(): Int {
        var beams = mutableListOf(Pair(Point2D.origin(), Direction.RIGHT))
        var tilesSeen = 0

        while(beams.isNotEmpty() && tilesSeen < 10000) {
            beams = beams.fold(mutableListOf()) { acc, (position, beamDirection) ->
                val tile = getTile(position)
                tile.isEnergised = true
                tilesSeen++

                if (tile.isMirror()) {
                    val reflectedDirection = tile.reflectionDirection(beamDirection)
                    val newPosition = position.shift(reflectedDirection)
                    if (hasRecorded(newPosition)) {
                        acc.add(Pair(newPosition, reflectedDirection))
                    }
                } else if (tile.isSplitter()) {
                    val splitDirections = tile.splitDirections(beamDirection)
                    splitDirections.forEach { newDirection ->
                        val newPosition = position.shift(newDirection)
                        if (hasRecorded(newPosition)) {
                            acc.add(Pair(newPosition, newDirection))
                        }
                    }
                } else if (tile.isEmpty()) {
                    val newPosition = position.shift(beamDirection)
                    if (hasRecorded(newPosition)) {
                        acc.add(Pair(newPosition, beamDirection))
                    }
                }

                acc
            }
        }

        return filterTiles { tile -> tile.isEnergised }.keys.count()
    }
}