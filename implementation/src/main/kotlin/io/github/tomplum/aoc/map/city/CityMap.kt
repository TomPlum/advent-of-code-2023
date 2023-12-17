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
            CrucibleLocation(Point2D.origin(), Direction.RIGHT, true, 0),
            CrucibleLocation(Point2D.origin(), Direction.UP, true, 0)
        ))
    }

    fun directUltraCrucibleFromLavaPoolToMachinePartsFactory(): Int {
        return traverseCityUltra(listOf(
            CrucibleLocation(Point2D.origin(), Direction.RIGHT, true, 0),
            CrucibleLocation(Point2D.origin(), Direction.UP, true, 0)
        ))
    }

    data class CrucibleLocation(
        val position: Point2D,
        val direction: Direction,
        val isMovingStraight: Boolean,
        var consecutiveSameDirectionSteps: Int = 1
    )

    data class Node<T>(val value: T, val distance: Int): Comparable<Node<T>> {
        override fun compareTo(other: Node<T>): Int {
            return distance.compareTo(other.distance)
        }
    }

    fun <N> djikstra(
        startingPositions: Collection<N>,
        evaluateAdjacency: (currentNode: Node<N>) -> Collection<Node<N>>,
        processNode: (currentNode: Node<N>, adjacentNode: Node<N>) -> Node<N>,
        terminates: (currentNode: Node<N>) -> Boolean
    ): Int {
        // A map of nodes and the shortest distance from the given starting positions to it
        val distance = mutableMapOf<N, Int>()

        // Unsettled nodes that are yet to be evaluated. Prioritised by their distance from the start
        val next = PriorityQueue<Node<N>>()

        // Settled nodes whose shortest path has been calculated and need not be evaluated again
        val settled = mutableSetOf<N>()

        startingPositions.forEach { startingPosition ->
            next.offer(Node(startingPosition, 0))
        }

        while(next.isNotEmpty()) {
            // Take the next node from the queue, ready for evaluation
            val currentNode = next.poll()

            // Considered the current node settled now
            settled.add(currentNode.value)

            // If the terminal condition has been met
            // (I.e. the current node is the location we want to find the shortest path to)
            // then we stop and return the current known shortest distance to it
            if (terminates(currentNode)) {
                return currentNode.distance
            }

            // Find all the adjacent nodes to the current one (as per the given predicate)
            // and evaluate each one
            evaluateAdjacency(currentNode).forEach { adjacentNode ->
                // Perform any additional processing on the adjacent node before evaluation
                val evaluationNode = processNode(currentNode, adjacentNode)

                if (!settled.contains(evaluationNode.value)) {
                    // The new shortest path to the adjacent node is the current nodes distance
                    // plus the weight of the node being evaluated
                    val updatedDistance = currentNode.distance + evaluationNode.distance

                    // If the distance of this new path is shorter than the shortest path we're
                    // already aware of, then we can update it since we've found a shorter one
                    if (updatedDistance < distance.getOrDefault(evaluationNode.value, Int.MAX_VALUE)) {
                        // Track the new shortest path
                        distance[evaluationNode.value] = updatedDistance

                        // Queue up the adjacent node to continue down that path
                        next.add(Node(evaluationNode.value, updatedDistance))
                    }
                }
            }
        }

        throw IllegalStateException("Could not find a path from the given starting positions to the node indicated by the terminates predicate.")
    }

    private fun traverseCity(startingPositions: List<CrucibleLocation>): Int {
        return djikstra(
            startingPositions,
            evaluateAdjacency = { (currentNode) ->
                val (currentPos, currentDirection, _, consecutiveSteps) = currentNode

                currentPos.let { pos ->
                    val rightDirection = currentDirection.rotate(90)
                    val rightPosition = pos.shift(rightDirection)
                    val right = CrucibleLocation(rightPosition, rightDirection, false)

                    val leftDirection = currentDirection.rotate(-90)
                    val leftPosition = pos.shift(leftDirection)
                    val left = CrucibleLocation(leftPosition, leftDirection, false)

                    val candidates = mutableListOf(left, right)

                    if (consecutiveSteps < 3) {
                        val straightPosition = pos.shift(currentDirection)
                        val straight = CrucibleLocation(straightPosition, currentDirection, true)
                        candidates.add(straight)
                    }

                    candidates.filter { (position) -> hasRecorded(position) }
                        .map { location -> Node(location, getTile(location.position).value) }
                }
            },
            processNode = { currentNode, adjacentNode ->
                val updatedConsecutiveSteps = if (adjacentNode.value.isMovingStraight) {
                    currentNode.value.consecutiveSameDirectionSteps + 1
                } else 1

                Node(adjacentNode.value.apply { consecutiveSameDirectionSteps = updatedConsecutiveSteps }, adjacentNode.distance)
            },
            terminates = { currentNode ->
                val xMax = xMax()!!
                val yMax = yMax()!!
                val factoryLocation = Point2D(xMax, yMax)
                currentNode.value.position == factoryLocation
            }
        )
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
            val (currentPos, currentDirection, _, consecutiveSteps) = currentNode

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
                val adjacentCrucibleLocation = CrucibleLocation(adjacentPos, adjacentDirection, isMovingStraight, updatedConsecutiveSteps)

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