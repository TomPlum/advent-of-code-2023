package io.github.tomplum.aoc.maze

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class PipeMaze(data: List<String>) : AdventMap2D<PipeMazeTile>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = PipeMazeTile(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun calculateStepsToFarthestPosition(): Int {
        val start = filterTiles { tile -> tile.isStartingPosition() }.entries.first()

        val candidates = mutableListOf(start.key)

        // TODO: make programmatic
        addTile(start.key, PipeMazeTile('F'))

        var steps = 0
        val seen = mutableMapOf<Point2D, PipeMazeTile>()

        while(candidates.isNotEmpty()) {
            val traversableTiles = candidates.map { current ->
                val currentTile = getTile(current)
                val adj = filterPoints(current.orthogonallyAdjacent().toSet())
                    .filterNot { it.value.isGround() }
                    .filter { it.key !in seen }

                adj.filter { (targetPos, targetTile) ->
                    val direction = if (current.x == targetPos.x) {
                        current.yRelativeDirection(targetPos)
                    } else {
                        targetPos.xRelativeDirection(current)
                    }!!.first

                    currentTile.canConnectTo(targetTile, direction)
                }
            }

            candidates.clear()

            seen.putAll(traversableTiles.flatMap { it.map { it.toPair() } })
            if (traversableTiles.isNotEmpty()) {
                steps++
            }
            candidates.addAll(traversableTiles.flatMap { it.keys })
        }

        return steps // TODO: Fix off by one error
    }

    fun determinePointsInsidePipeLoop(): Int {
        val start = filterTiles { tile -> tile.isStartingPosition() }.entries.first()

        val candidates = mutableListOf(start.key)

        // TODO: make programmatic
        addTile(start.key, PipeMazeTile('F'))

        var steps = 0
        val seen = mutableMapOf<Point2D, PipeMazeTile>()

        while(candidates.isNotEmpty()) {
            val traversableTiles = candidates.map { current ->
                val currentTile = getTile(current)
                val adj = filterPoints(current.orthogonallyAdjacent().toSet())
                    .filterNot { it.value.isGround() }
                    .filter { it.key !in seen }

                adj.filter { (targetPos, targetTile) ->
                    val direction = if (current.x == targetPos.x) {
                        current.yRelativeDirection(targetPos)
                    } else {
                        targetPos.xRelativeDirection(current)
                    }!!.first

                    currentTile.canConnectTo(targetTile, direction)
                }
            }

            candidates.clear()

            seen.putAll(traversableTiles.flatMap { it.map { it.toPair() } })
            if (traversableTiles.isNotEmpty()) {
                steps++
            }
            candidates.addAll(traversableTiles.flatMap { it.keys })
        }

        val xMin = seen.minBy { it.key.x }.key.x
        val xMax = seen.maxBy { it.key.x }.key.x
        val yMin = seen.minBy { it.key.y }.key.y
        val yMax = seen.maxBy { it.key.y }.key.y

        val candidatePoints = (xMin..xMax).flatMap { x ->
            (yMin..yMax).map { y ->
                Point2D(x, y)
            }
        }.filter { getTile(it).isGround() }

        // 46 pipe tiles in boundary, 28 vertical ones
        val verticalBoundaryTiles = seen
            .filter { it.value.type in listOf(PipeType.VERTICAL, PipeType.NORTH_EAST_RIGHT_ANGLE, PipeType.NORTH_WEST_RIGHT_ANGLE) }

        val innerPoints = getDataMap().entries.filter { it.key !in seen }.map { it.key }.filter { candidate ->
            val boundaryCollisions = (candidate.x..xMax()!!).filter {
                Point2D(it, candidate.y) in verticalBoundaryTiles
            }

            boundaryCollisions.size % 2 != 0
        }

        return innerPoints.size // TODO: Fix off by one error
    }

    private fun determineStartingTilePipeType(start: Point2D): PipeType {
        val adjacent = filterPoints(start.orthogonallyAdjacent().toSet())
            .filterNot { it.value.isGround() }
        // TODO: Implement programmatically
        return PipeType.STARTING
    }
}