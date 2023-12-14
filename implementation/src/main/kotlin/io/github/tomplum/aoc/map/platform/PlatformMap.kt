package io.github.tomplum.aoc.map.platform

import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point
import io.github.tomplum.libs.math.point.Point2D

class PlatformMap(data: List<String>): AdventMap2D<PlatformTile>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = PlatformTile(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun calculateNorthSupportBeamsTotalLoad(): Int {
        var northernMostAvailableY = 0

        (xMin()!!..xMax()!!).forEach { x ->
            (yMin()!!..yMax()!!).forEach { y ->
                val position = Point2D(x, y)
                val tile = getTile(position)

                if (tile.isEmptySpace()) {
                    northernMostAvailableY = position.y
                }

                if (tile.isCubicRock()) {
                    northernMostAvailableY + position.y + 1
                }

                if (tile.isRoundedRock()) {
                    val oldRock = removeTile(position)
                    addTile(position, PlatformTile('.'))
                    addTile(Point2D(position.x, getNorthernMostAvailablePlatform(x, y)), oldRock!!)

                    northernMostAvailableY += 1
                }
            }
        }

        return calculateCurrentNorthSupportBeamLoad()
    }

    fun calculateNorthSupportBeamsTotalLoad(cycles: Int): Int {
        val directions = listOf(Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT)
        val cache = mutableMapOf<String, Pair<Int, Int>>()

        repeat(cycles) { currentCycleIndex ->
            directions.forEach { direction ->
                tiltPlatform(direction)
            }
//            AdventLogger.error("Cycle ${currentCycleIndex + 1}")
//            AdventLogger.error(this)

            val snapshot = filterTiles { tile -> tile.isRoundedRock() }.keys.toList().joinToString(transform = Point2D::toString)
            val currentLoad = calculateCurrentNorthSupportBeamLoad()
//            AdventLogger.error(currentLoad)

            if (cache.containsKey(snapshot)) {
                val remainder = cycles % (currentCycleIndex + 1)
                return cache.entries.find { entry -> entry.value.second == remainder }?.value?.first ?: throw IllegalArgumentException("Couldn't find cycle")
            }

            cache[snapshot] = currentLoad to currentCycleIndex + 1
        }

        return filterTiles { tile -> tile.isRoundedRock() }.keys.sumOf { pos ->
            (yMax()!! + 1) - pos.y
        }
    }

    val xMin = xMin()!!
    val xMax = xMax()!!
    val yMin = yMin()!!
    val yMax = yMax()!!

    private fun tiltPlatform(direction: Direction) {


        val outerRange =  when(direction) {
            Direction.UP -> xMin..xMax
            Direction.DOWN -> xMax downTo xMin
            Direction.RIGHT -> yMin..yMax
            Direction.LEFT -> yMax downTo yMin
            else -> throw IllegalArgumentException("Invalid Direction [$direction]")
        }

        val innerRange = when(direction) {
            Direction.UP -> yMin..yMax
            Direction.DOWN -> yMax downTo yMin
            Direction.RIGHT -> xMax downTo xMin
            Direction.LEFT -> xMin..xMax
            else -> throw IllegalArgumentException("Invalid Direction [$direction]")
        }

        outerRange.forEach { outer ->
            innerRange.forEach { inner ->
                val position = when(direction) {
                    Direction.UP, Direction.DOWN -> Point2D(outer, inner)
                    Direction.RIGHT,  Direction.LEFT -> Point2D(inner, outer)
                    else -> throw IllegalArgumentException("Invalid Direction [$direction]")
                }

                val tile = getTile(position)

                if (tile.isRoundedRock()) {
                    val oldRock = removeTile(position)
                    addTile(position, PlatformTile('.'))

                    val newPosition = getAvailablePlatform(position, direction)
                    addTile(newPosition, oldRock!!)
                }
            }
        }
    }

    private fun getAvailablePlatform(currentPosition: Point2D, direction: Direction): Point2D {
        val path = getDataMap().filterKeys { pos ->
            when(direction) {
                Direction.UP, Direction.DOWN -> pos.x == currentPosition.x
                Direction.RIGHT, Direction.LEFT -> pos.y == currentPosition.y
                else -> throw IllegalArgumentException("Invalid Direction [$direction]")
            }
        }.let { path -> when(direction) {
            Direction.UP -> path.entries.sortedByDescending { (pos) -> pos.y }
            Direction.DOWN -> path.entries.sortedBy { (pos) -> pos.y }
            Direction.RIGHT -> path.entries.sortedBy { (pos) -> pos.x }
            Direction.LEFT -> path.entries.sortedByDescending { (pos) -> pos.x }
            else -> throw IllegalArgumentException("Invalid Direction [$direction]")
        } }

        var currentEmptyCandidate = when(direction) {
            Direction.UP, Direction.DOWN -> Int.MAX_VALUE
            Direction.RIGHT, Direction.LEFT -> Int.MIN_VALUE
            else -> throw IllegalArgumentException("Invalid Direction [$direction]")
        }

        path.forEach { (pos, tile) ->
            val isCubicRockGoingToBlockMovement = when(direction) {
                Direction.UP -> pos.y < currentPosition.y
                Direction.DOWN -> pos.y > currentPosition.y
                Direction.RIGHT -> pos.x > currentPosition.x
                Direction.LEFT -> pos.x < currentPosition.x
                else -> throw IllegalArgumentException("Invalid Direction [$direction]")
            }

            if (tile.isCubicRock() && isCubicRockGoingToBlockMovement) {
                return when(direction) {
                    Direction.UP -> Point2D(currentPosition.x, currentEmptyCandidate)
                    Direction.DOWN -> Point2D(currentPosition.x, currentEmptyCandidate)
                    Direction.RIGHT -> Point2D(currentEmptyCandidate, currentPosition.y)
                    Direction.LEFT -> Point2D(currentEmptyCandidate, currentPosition.y)
                    else -> throw IllegalArgumentException("Invalid Direction [$direction]")
                }
            }

            if (tile.isEmptySpace()) {
                currentEmptyCandidate = when(direction) {
                    Direction.UP, Direction.DOWN -> pos.y
                    Direction.RIGHT, Direction.LEFT -> pos.x
                    else -> throw IllegalArgumentException("Invalid Direction [$direction]")
                }
            }
        }

        return when(direction) {
            Direction.UP -> Point2D(currentPosition.x, currentEmptyCandidate)
            Direction.DOWN -> Point2D(currentPosition.x, currentEmptyCandidate)
            Direction.RIGHT -> Point2D(currentEmptyCandidate, currentPosition.y)
            Direction.LEFT -> Point2D(currentEmptyCandidate, currentPosition.y)
            else -> throw IllegalArgumentException("Invalid Direction [$direction]")
        }
    }

    private fun getNorthernMostAvailablePlatform(x: Int, yRock: Int): Int {
        val column = getDataMap().filterKeys { pos -> pos.x == x }.entries.sortedByDescending { (pos) -> pos.y }
        var northernMostEmpty = Int.MAX_VALUE

        column.forEach { (pos, tile) ->
            if (tile.isCubicRock() && pos.y < yRock) {
                return northernMostEmpty
            }

            if (tile.isEmptySpace()) {
                northernMostEmpty = pos.y
            }
        }

        // throw IllegalArgumentException("Cannot find available platform for ($x,$yRock)")
        return northernMostEmpty
    }

    private fun calculateCurrentNorthSupportBeamLoad() = filterTiles { tile ->
        tile.isRoundedRock()
    }.keys.sumOf { pos -> (yMax()!! + 1) - pos.y }
}