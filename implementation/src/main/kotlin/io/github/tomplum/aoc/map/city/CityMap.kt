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
        return traverseCity(listOf(
            CrucibleLocation(Point2D.origin(), Direction.RIGHT, 0),
            CrucibleLocation(Point2D.origin(), Direction.UP, 0)
        ))
    }

    fun directUltraCrucibleFromLavaPoolToMachinePartsFactory(): Int {
        return traverseCityUltra(listOf(
            CrucibleLocation(Point2D.origin(), Direction.RIGHT, 0),
            CrucibleLocation(Point2D.origin(), Direction.UP, 0)
        ))
    }

    data class CrucibleLocation(
        val position: Point2D,
        val direction: Direction,
        var consecutiveSameDirectionSteps: Int = 1
    )

    data class Node<T>(val value: T, val distance: Int): Comparable<Node<T>> {
        override fun compareTo(other: Node<T>): Int {
            return distance.compareTo(other.distance)
        }
    }

    private fun traverseCity(startingPositions: List<CrucibleLocation>): Int {
        val xMax = xMax()!!
        val yMax = yMax()!!
        val factoryLocation = Point2D(xMax, yMax)

        val heatLoss = mutableMapOf<CrucibleLocation, Int>()
        val next = PriorityQueue<Node<CrucibleLocation>>()
        val settled = mutableSetOf<CrucibleLocation>()

        startingPositions.forEach { startingPosition ->
            next.offer(Node(startingPosition, 0))
        }

        while(next.isNotEmpty()) {
            val (currentNode, currentDistance) = next.poll()
            val (currentPos, currentDirection, consecutiveSteps) = currentNode

            settled.add(currentNode)

            val nextPositionCandidates = currentPos.let { pos ->
                val straight = Triple(pos.shift(currentDirection), currentDirection, true)

                val rightDirection = currentDirection.rotate(90)
                val right = Triple(pos.shift(rightDirection), rightDirection, false)

                val leftDirection = currentDirection.rotate(-90)
                val left = Triple(pos.shift(leftDirection), leftDirection, false)

                val candidates = mutableListOf(left, right)
                if (consecutiveSteps < 3) {
                    candidates.add(straight)
                }

                if (currentPos == factoryLocation) {
                    return currentDistance
                } else {
                    candidates.filter { (pos) -> hasRecorded(pos) }
                }
            }

            nextPositionCandidates.forEach { (adjacentPos, adjacentDirection, isMovingStraight) ->
                val updatedConsecutiveSteps = if (isMovingStraight) consecutiveSteps + 1 else 1
                val adjacentCrucibleLocation = CrucibleLocation(adjacentPos, adjacentDirection, updatedConsecutiveSteps)

                if (!settled.contains(adjacentCrucibleLocation)) {
                    val updatedDistance = currentDistance + getTile(adjacentPos).value
                    if (updatedDistance < heatLoss.getOrDefault(adjacentCrucibleLocation, Int.MAX_VALUE)) {
                        heatLoss[adjacentCrucibleLocation] = updatedDistance
                        next.add(Node(adjacentCrucibleLocation, updatedDistance))
                    }
                }
            }
        }

        return 0
    }

    private fun traverseCityUltra(startingPositions: List<CrucibleLocation>): Int {
        val xMax = xMax()!!
        val yMax = yMax()!!
        val factoryLocation = Point2D(xMax, yMax)

        val heatLoss = mutableMapOf<CrucibleLocation, Int>()
        val next = PriorityQueue<Node<CrucibleLocation>>()
        val settled = mutableSetOf<CrucibleLocation>()

        startingPositions.forEach { startingPosition ->
            next.offer(Node(startingPosition, 0))
        }

        while(next.isNotEmpty()) {
            val (currentNode, currentDistance) = next.poll()
            val (currentPos, currentDirection, consecutiveSteps) = currentNode

            settled.add(currentNode)

            val nextPositionCandidates = currentPos.let { pos ->
                val candidates = mutableListOf<Triple<Point2D, Direction, Boolean>>()

                if (consecutiveSteps >= 4) {
                    val rightDirection = currentDirection.rotate(90)
                    val right = Triple(pos.shift(rightDirection), rightDirection, false)
                    candidates.add(right)

                    val leftDirection = currentDirection.rotate(-90)
                    val left = Triple(pos.shift(leftDirection), leftDirection, false)
                    candidates.add(left)
                }

                if (consecutiveSteps < 10) {
                    val straight = Triple(pos.shift(currentDirection), currentDirection, true)
                    candidates.add(straight)
                }

                if (currentPos == factoryLocation && consecutiveSteps >= 4) {
                    return currentDistance
                } else {
                    candidates.filter { (pos) -> hasRecorded(pos) }
                }
            }

            nextPositionCandidates.forEach { (adjacentPos, adjacentDirection, isMovingStraight) ->
                val updatedConsecutiveSteps = if (isMovingStraight) consecutiveSteps + 1 else 1
                val adjacentCrucibleLocation = CrucibleLocation(adjacentPos, adjacentDirection, updatedConsecutiveSteps)

                if (!settled.contains(adjacentCrucibleLocation)) {
                    val updatedDistance = currentDistance + getTile(adjacentPos).value
                    if (updatedDistance < heatLoss.getOrDefault(adjacentCrucibleLocation, Int.MAX_VALUE)) {
                        heatLoss[adjacentCrucibleLocation] = updatedDistance
                        next.add(Node(adjacentCrucibleLocation, updatedDistance))
                    }
                }
            }
        }

        return 0
    }
}