package io.github.tomplum.aoc.map.city

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.*

class CityMap(data: List<String>): AdventMap2D<CityBlock>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = CityBlock(column.toString().toInt())
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun directCrucibleFromLavaPoolToMachinePartsFactory(): Int {
        return traverseCity(CrucibleLocation(Point2D.origin(), Direction.RIGHT))
    }

    data class CrucibleLocation(val position: Point2D, val direction: Direction, val history: List<Direction> = mutableListOf()): Comparable<CrucibleLocation> {
        override fun compareTo(other: CrucibleLocation): Int {
            return other.position.compareTo(position)
        }
    }

    private fun traverseCity(startingPosition: CrucibleLocation): Int {
        val factoryLocation = Point2D(xMax()!!, yMax()!!)

        val heatLoss = mutableMapOf<Point2D, Int>()
        val next = PriorityQueue<CrucibleLocation>()

        next.offer(startingPosition)

        heatLoss[startingPosition.position] = 0

        while(next.isNotEmpty()) {
            val (currentPos, currentDirection, currentHistory) = next.poll()

            val currentHeatLoss = heatLoss[currentPos]!!

            val nextPositionCandidates = currentPos.let { pos ->
                val straight = pos.shift(currentDirection) to currentDirection

                val rightDirection = currentDirection.rotate(90)
                val right = pos.shift(rightDirection) to rightDirection

                val leftDirection = currentDirection.rotate(-90)
                val left = pos.shift(leftDirection) to leftDirection

                val candidates = mutableListOf(left, right)
                if (currentHistory.takeLast(3).distinct().size > 1) {
                    candidates.add(straight)
                }
                val validCandidates = candidates.filter { (pos) -> hasRecorded(pos) }

                if (currentPos == factoryLocation) {
                    emptyList()
                } else {
                    validCandidates
                }
            }

            nextPositionCandidates.forEach { (adjacentPos, adjacentDirection) ->
                val updatedHeatLoss = currentHeatLoss + getTile(adjacentPos).value
                if (updatedHeatLoss < heatLoss.getOrDefault(adjacentPos, Int.MAX_VALUE)) {
                    heatLoss[adjacentPos] = updatedHeatLoss
                    next.add(CrucibleLocation(adjacentPos, adjacentDirection, currentHistory + adjacentDirection))
                }
            }
        }

        return heatLoss[factoryLocation]!!
    }
}