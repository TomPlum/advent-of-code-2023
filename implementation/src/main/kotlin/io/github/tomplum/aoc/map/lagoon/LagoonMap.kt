package io.github.tomplum.aoc.map.lagoon

import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

class LagoonMap(digPlan: List<String>): AdventMap2D<LagoonTile>() {
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

    fun calculateVolume(): Long {
        var currentPosition = Point2D.origin()
        val yCache = mutableMapOf<Int, List<Point2D>>()
        val xCache = mutableMapOf<Int, List<Point2D>>()
        val vertices = mutableListOf<Point2D>()

        instructions.forEach { (direction, distance) ->
            addTile(currentPosition, LagoonTile('#'))
            vertices.add(currentPosition)
            yCache[currentPosition.y] = yCache.getOrDefault(currentPosition.y, mutableListOf()) + currentPosition
            xCache[currentPosition.x] = yCache.getOrDefault(currentPosition.x, mutableListOf()) + currentPosition

            val nextPositions = (1..distance).fold(listOf(currentPosition)) { acc, _ ->
                val newPos = acc.last().shift(direction)
                acc + newPos
            }

            nextPositions.dropLast(1).forEach { position ->
                addTile(position, LagoonTile('#'))
                yCache[position.y] = yCache.getOrDefault(position.y, mutableListOf()) + position
                xCache[position.x] = yCache.getOrDefault(position.x, mutableListOf()) + position
            }

            currentPosition = nextPositions.last()
        }

        val area = vertices.area()
        val boundaryPoints = filterTiles { it.isTrench() }.count()
        val enclosedPoints = (area - boundaryPoints) / 2L + 1L
        return boundaryPoints + enclosedPoints

      /*  val xMin = xMin()!!
        val xMax = xMax()!!
        val yMin = yMin()!!
        val yMax = yMax()!!

        (xMin..xMax).forEach { x ->
            (yMin..yMax).forEach { y ->
                val position = Point2D(x, y)
                val isBoundedX = yCache[position.y]!!.count { it.x > position.x } % 2 == 1
                val isBoundedY = xCache[position.x]!!.count { it.y > position.y } % 2 == 1

                if (!hasRecorded(position) && isBoundedX && isBoundedY) {
                    addTile(position, LagoonTile('#'))
                }
            }
        }*/

/*        filterTiles { tile -> tile.isGroundLevelTerrain() }
            .filter { (pos) -> cache[pos.y]!!.count { it.x > pos.x } % 2 != 0 }
            .forEach { (pos) -> addTile(pos, LagoonTile('#')) }*/

//        return filterTiles { tile -> tile.isTrench() }.count()
    }

    private fun Point2D.isEnclosed(boundaryPredicate: (tile: LagoonTile) -> Boolean): Boolean {
        val xBoundaryTiles = filterTiles(boundaryPredicate).filter { it.key.y == this.y }
        return xBoundaryTiles.count { it.key.x > this.x } % 2 != 0
    }

    /*private fun List<Point2D>.area(): Int {
        val vertices = this.size
        var sum1 = 0
        var sum2 = 0

        (0..vertices - 2).forEach { i ->
            sum1 += this[i].x * this[i + 1].y
            sum2 += this[i].y * this[i + 1].x
        }

        sum1 += this[vertices - 1].x * this.first().y
        sum2 += this.first().x * this[vertices - 1].y

        return abs(sum1 - sum2) / 2
    }*/

/*    private fun List<Point2D>.area(): Int {
        val n = this.size
        var a = 0
        for (i in 0 until n - 1) {
            a += this[i].x * this[i + 1].y - this[i + 1].x * this[i].y
        }
        return abs(a + this[n - 1].x * this[0].y - this[0].x * this[n -1].y)
    }*/

    private fun List<Point2D>.area(): Long {
        var result = 0L
        for (i in 1..this.size) {
            result += this[i % this.size].y.toLong() * (this[(i - 1) % this.size].x.toLong() - this[(i + 1) % this.size].x.toLong())
        }
        return abs(result)
    }
}