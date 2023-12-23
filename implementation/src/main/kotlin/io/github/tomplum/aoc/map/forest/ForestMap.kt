package io.github.tomplum.aoc.map.forest

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class ForestMap(data: List<String>): AdventMap2D<ForestTile>() {

    private val targetDestination: Point2D
    private val visited = mutableMapOf<Point2D, Boolean>()

    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = ForestTile(column)
                val position = Point2D(x, y)

                visited[position] = false
                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }

        targetDestination = getDataMap().entries
            .filter { (pos) -> pos.y == yMax()!! }
            .single { (_, tile) -> tile.isTraversablePath() }
            .key
    }


    fun longestHikeSteps() = findLongestPath(
        currentPosition = getDataMap().entries
            .filter { (pos) -> pos.y == 0 }
            .single { (_, tile) -> tile.isTraversablePath() }
            .key,
        visited = visited
    )

    private fun findLongestPath(currentPosition: Point2D, visited: MutableMap<Point2D, Boolean>, distance: Int = 0): Int {
        if (currentPosition == targetDestination) {
            return distance
        }

        visited[currentPosition] = true

        val longestHike = currentPosition.getTraversableNeighbours()
            .filterNot { pos -> visited[pos] == true }
            .maxOfOrNull { nextPosition ->
                findLongestPath(nextPosition, visited, distance + 1)
            }

        visited[currentPosition] = false

        return longestHike ?: 0
    }

    private fun Point2D.getTraversableNeighbours(): List<Point2D> {
        val tile = getTile(this)
        return when {
            tile.isSteepSlope() -> listOf(this.getIcySlopeDestination(tile.value))
            else -> this.orthogonallyAdjacent()
                .filter { pos -> getTile(pos, ForestTile('#')).isTraversablePath() }
        }
    }

    private fun Point2D.getIcySlopeDestination(slope: Char) = when(slope) {
        '>' -> this.shift(Direction.RIGHT)
        '<' -> this.shift(Direction.LEFT)
        'v' -> this.shift(Direction.UP)
        '^' -> this.shift(Direction.DOWN)
        else -> throw IllegalArgumentException("$slope is not a valid slope identifier.")
    }
}