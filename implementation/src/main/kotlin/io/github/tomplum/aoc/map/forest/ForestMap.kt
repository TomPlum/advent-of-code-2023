package io.github.tomplum.aoc.map.forest

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class ForestMap(data: List<String>): AdventMap2D<ForestTile>() {

    private val startingPosition: Point2D
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

        startingPosition = getDataMap().entries
            .filter { (pos) -> pos.y == 0 }
            .single { (_, tile) -> tile.isTraversable() }
            .key

        targetDestination = getDataMap().entries
            .filter { (pos) -> pos.y == yMax()!! }
            .single { (_, tile) -> tile.isTraversable() }
            .key
    }

    fun longestHikeSteps() = findLongestPath(
        currentPosition = startingPosition,
        visited = visited,
        findTraversableTiles = { currentPosition ->
            val tile = getTile(currentPosition)
            when {
                tile.isSteepSlope() -> listOf(currentPosition.getIcySlopeDestination(tile.value) to 1)
                else -> currentPosition.orthogonallyAdjacent()
                    .filter { pos -> getTile(pos, ForestTile('#')).isTraversable() }
                    .map { pos -> pos to 1 }
            }
        }
    )

    fun longestHikeStepsNoSlopes(): Int {
        val junctions = mutableMapOf(
            startingPosition to mutableListOf<Pair<Point2D, Int>>(),
            targetDestination to mutableListOf()
        )

        filterTiles { tile -> tile.isPath() }
            .filter { (pos) -> pos.orthogonallyAdjacent().filter { getTile(it, ForestTile('#')).isTraversable() }.size > 2 }
            .forEach { (pos) -> junctions[pos] = mutableListOf() }

        junctions.keys.forEach { junction ->
            var current = setOf(junction)
            val visited = mutableSetOf(junction)
            var distance = 0

            while (current.isNotEmpty()) {
                distance++
                current = buildSet {
                    current.forEach { currentPosition ->
                        currentPosition.orthogonallyAdjacent()
                            .filter { getTile(currentPosition, ForestTile('*')).isTraversable() }
                            .filter { pos -> pos !in visited }
                            .forEach { pos ->
                                if (pos in junctions) {
                                    junctions.getValue(junction).add(pos to distance)
                                } else {
                                    add(pos)
                                    visited.add(pos)
                                }
                            }
                    }
                }
            }
        }

        return findLongestPath(
            currentPosition = startingPosition,
            visited = visited,
            findTraversableTiles = { currentPosition ->
                junctions.getValue(currentPosition)
            }
        )
    }

    private fun findLongestPath(
        currentPosition: Point2D,
        visited: MutableMap<Point2D, Boolean>,
        distance: Int = 0,
        findTraversableTiles: (currentPosition: Point2D) -> List<Pair<Point2D, Int>>
    ): Int {
        if (currentPosition == targetDestination) {
            return distance
        }

        visited[currentPosition] = true

        val longestHike = findTraversableTiles(currentPosition)
            .filterNot { (pos) -> visited[pos] == true }
            .maxOfOrNull { (nextPosition, pathWeight) ->
                findLongestPath(nextPosition, visited, distance + pathWeight, findTraversableTiles)
            }

        visited[currentPosition] = false

        return longestHike ?: 0
    }

    private fun Point2D.getIcySlopeDestination(slope: Char) = when(slope) {
        '>' -> this.shift(Direction.RIGHT)
        '<' -> this.shift(Direction.LEFT)
        'v' -> this.shift(Direction.UP)
        '^' -> this.shift(Direction.DOWN)
        else -> throw IllegalArgumentException("$slope is not a valid slope identifier.")
    }
}