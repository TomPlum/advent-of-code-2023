package io.github.tomplum.aoc.map.lagoon

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class LagoonMap(private val digPlan: List<String>): AdventMap2D<LagoonTile>() {
    private val instructions = digPlan.map { line ->
        val (directionCode, distance, hex) = line.split(" ")

        val direction = when(directionCode) {
            "R" -> Direction.RIGHT
            "L" -> Direction.LEFT
            "U" -> Direction.DOWN
            "D" -> Direction.UP
            else -> throw IllegalArgumentException("Invalid Directional Code [$directionCode]")
        }

        Triple(direction, distance.toInt(), hex.removeSurrounding("(", ")"))
    }

    fun calculateVolume(): Int {
        var currentPosition = Point2D.origin()

        instructions.forEach { (direction, distance) ->
            addTile(currentPosition, LagoonTile('#'))

            val nextPositions = (1..distance).fold(listOf(currentPosition)) { acc, _ ->
                val newPos = acc.last().shift(direction)
                acc + newPos
            }

            nextPositions.dropLast(1).forEach { position ->
                addTile(position, LagoonTile('#'))
            }

            currentPosition = nextPositions.last()
        }

        val xMin = xMin()!!
        val xMax = xMax()!!
        val yMin = yMin()!!
        val yMax = yMax()!!

        (xMin..xMax).forEach { x ->
            (yMin..yMax).forEach { y ->
                if (!hasRecorded(Point2D(x, y))) {
                    addTile(Point2D(x, y), LagoonTile('.'))
                }
            }
        }

        filterTiles { tile -> tile.isGroundLevelTerrain() }
            .filter { (pos) -> pos.isEnclosed { tile -> tile.isTrench() } }
            .forEach { (pos) -> addTile(pos, LagoonTile('#')) }

        return filterTiles { tile -> tile.isTrench() }.count()
    }

    private fun Point2D.isEnclosed(boundaryPredicate: (tile: LagoonTile) -> Boolean): Boolean {
        val xBoundaryTiles = filterTiles(boundaryPredicate).filter { it.key.y == this.y }
        return xBoundaryTiles.count { it.key.x > this.x } % 2 != 0
    }
}