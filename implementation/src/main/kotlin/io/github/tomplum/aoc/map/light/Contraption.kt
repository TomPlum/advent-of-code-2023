package io.github.tomplum.aoc.map.light

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point
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
        val start = Point2D.origin()
        val seen = hashSetOf(start to Direction.RIGHT)
        val queue = ArrayDeque<Pair<Point2D, Direction>>().apply {
            add(start to Direction.RIGHT)
        }

        while (queue.isNotEmpty()) {
            val (position, beamDirection) = queue.removeFirst()
            val tile = getTile(position)

            val nextDirections = if (tile.isMirror()) {
                val reflectedDirection = tile.reflectionDirection(beamDirection)
                val newPosition = position.shift(reflectedDirection)
                    listOf(Pair(newPosition, reflectedDirection))
            } else if (tile.isSplitter()) {
                val splitDirections = tile.splitDirections(beamDirection)
                splitDirections.map { newDirection ->
                    val newPosition = position.shift(newDirection)
                    Pair(newPosition, newDirection)
                }
            } else if (tile.isEmpty()) {
                val newPosition = position.shift(beamDirection)
                listOf(Pair(newPosition, beamDirection))
            } else {
                throw IllegalArgumentException("Invalid Tile")
            }

            nextDirections.forEach { (nextPosition, nextDirection) ->
                val nextPair = nextPosition to nextDirection
                if (nextPair !in seen && hasRecorded(nextPosition)) {
                    queue.add(nextPair)
                    seen += nextPair
                }
            }
        }

        return seen.map { (pos) -> pos }.toSet().size
    }

    fun countEnergisedTilesFromAllEdgeEntryPoints(): Int {
        val xMin = xMin()!!
        val xMax = xMax()!!
        val yMin = yMin()!!
        val yMax = yMax()!!

        val topStartingPoints = (xMin..xMax).map { x -> Point2D(x, yMin) to Direction.UP }
        val rightStartingPoints = (yMin..yMax).map { y -> Point2D(xMax, y) to Direction.LEFT }
        val bottomStartingPoints = (xMin..xMax).map { x -> Point2D(x, yMax) to Direction.DOWN }
        val leftStartingPoints = (yMin..yMax).map { y -> Point2D(xMin, y) to Direction.RIGHT }

        val entryPoints = topStartingPoints + rightStartingPoints + bottomStartingPoints + leftStartingPoints

        return entryPoints.distinctBy { (pos) -> pos }.maxOf { entry ->
            val seen = hashSetOf(entry)
            val queue = ArrayDeque<Pair<Point2D, Direction>>().apply {
                add(entry)
            }

            while (queue.isNotEmpty()) {
                val (position, beamDirection) = queue.removeFirst()
                val tile = getTile(position)

                val nextDirections = if (tile.isMirror()) {
                    val reflectedDirection = tile.reflectionDirection(beamDirection)
                    val newPosition = position.shift(reflectedDirection)
                    listOf(Pair(newPosition, reflectedDirection))
                } else if (tile.isSplitter()) {
                    val splitDirections = tile.splitDirections(beamDirection)
                    splitDirections.map { newDirection ->
                        val newPosition = position.shift(newDirection)
                        Pair(newPosition, newDirection)
                    }
                } else if (tile.isEmpty()) {
                    val newPosition = position.shift(beamDirection)
                    listOf(Pair(newPosition, beamDirection))
                } else {
                    throw IllegalArgumentException("Invalid Tile")
                }

                nextDirections.forEach { (nextPosition, nextDirection) ->
                    val nextPair = nextPosition to nextDirection
                    if (nextPair !in seen && hasRecorded(nextPosition)) {
                        queue.add(nextPair)
                        seen += nextPair
                    }
                }
            }

            seen.map { (pos) -> pos }.toSet().size
        }
    }
}