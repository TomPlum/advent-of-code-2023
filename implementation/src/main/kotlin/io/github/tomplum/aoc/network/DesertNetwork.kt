package io.github.tomplum.aoc.network

class DesertNetwork(documents: List<String>) {
    private val directions = documents.first().trim().map { it }

    private val nodes = documents.drop(2).associate { line ->
        val info = line.filterNot { it == ' ' }
        val (node, destinations) = info.split("=")
        val (left, right) = destinations.removePrefix("(").removeSuffix(")").split(",")
        node to Pair(left, right)
    }

    fun stepsRequiredToReachEnd(): Int {
        var currentPosition = "AAA"
        var stepsTaken = 0

        while (currentPosition != "ZZZ") {
            directions.forEach { direction ->
                currentPosition = if (direction == 'R') {
                    nodes[currentPosition]!!.second
                } else {
                    nodes[currentPosition]!!.first
                }
                stepsTaken++
            }
        }

        return stepsTaken
    }

    fun stepsRequiredToReachAllEnds(): Int {
        val startingNodes = nodes.keys.filter { node -> node.last() == 'A' }
        val finishingNodes = nodes.keys.filter { node -> node.last() == 'Z' }

        val currentPosition = startingNodes.associateWith { node -> node }.toMutableMap()
        val stepsTaken = startingNodes.associateWith { _ -> 0 }.toMutableMap()

        currentPosition.keys.forEach { node ->
            while (currentPosition[node]!!.last() != 'Z') {
                directions.forEach { direction ->
                    currentPosition[node] = if (direction == 'R') {
                        nodes[currentPosition[node]]!!.second
                    } else {
                        nodes[currentPosition[node]]!!.first
                    }
                    stepsTaken[node] = (stepsTaken[node] ?: 0) + 1
                }
            }
        }

        return stepsTaken.values.max()
    }
}