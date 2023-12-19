package io.github.tomplum.aoc.map.lagoon

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

// TODO: Remove AdventMap and Tile and move packages
class LagoonMap(digPlan: List<String>): AdventMap2D<LagoonTile>() {
    private val instructions = digPlan.map { line ->
        val (directionCode, distance) = line.split(" ")

        val direction = when(directionCode) {
            "R" -> Direction.RIGHT
            "L" -> Direction.LEFT
            "U" -> Direction.DOWN
            "D" -> Direction.UP
            else -> throw IllegalArgumentException("Invalid Directional Code [$directionCode]")
        }

        direction to distance.toInt()
    }

    private val trueInstructions = digPlan.map { line ->
        val hex = line.split(" ").last()
        val distance = hex.substring(2, 7).toInt(radix = 16)
        val direction = when(val directionValue = hex.substring(7, 8).toLong(radix = 16)) {
            0L -> Direction.RIGHT
            2L -> Direction.LEFT
            3L -> Direction.DOWN
            1L -> Direction.UP
            else -> throw IllegalArgumentException("Invalid Directional Code [$directionValue]")
        }

        direction to distance
    }

    fun calculateVolume(): Long = findTrenchArea(instructions)

    fun calculateTrueVolume(): Long  = findTrenchArea(trueInstructions)

    private fun findTrenchArea(instructions: List<Pair<Direction, Int>>): Long {
        val vertices = mutableListOf<Point2D>()
        var currentPosition = Point2D.origin()
        var perimeter = 0L

        instructions.forEach { (direction, distance) ->
            currentPosition = currentPosition.shift(direction, distance)
            vertices += currentPosition
            perimeter += distance
        }

        val area = vertices.area()
        val enclosedPoints = (area - perimeter) / 2L + 1L
        return perimeter + enclosedPoints
    }

    // TODO: Migrate to libs and replace
    private fun List<Point2D>.area(): Long = (1..this.size)
        .asSequence()
        .map { i ->
            val vertices = this.size
            this[i % vertices].y.toLong() * (this[(i - 1) % vertices].x.toLong() - this[(i + 1) % vertices].x.toLong())
        }
        .sum()
        .let { area -> abs(area) }
}