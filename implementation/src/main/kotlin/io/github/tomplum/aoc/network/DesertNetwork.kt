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

    fun stepsRequiredToReachAllEnds(): Long {
        val startingNodes = nodes.keys.filter { node -> node.last() == 'A' }

        val currentPosition = startingNodes.associateWith { node -> node }.toMutableMap()
        val stepsTaken = startingNodes.associateWith { _ -> 0L }.toMutableMap()

        val endingSnapshots = startingNodes.associateWith { _ -> 0L }.toMutableMap()

        while (endingSnapshots.values.any { it == 0L }) {
            directions.forEach { direction ->
                currentPosition.keys.forEach { node ->
                    if (currentPosition[node]!!.last() == 'Z') {
                        endingSnapshots[node] = stepsTaken[node]!!
                    }

                    currentPosition[node] = if (direction == 'R') {
                        nodes[currentPosition[node]]!!.second
                    } else {
                        nodes[currentPosition[node]]!!.first
                    }
                    stepsTaken[node] = (stepsTaken[node] ?: 0) + 1
                }
            }
        }

        return endingSnapshots.values.toList().lcm()
    }

    // TODO: move to lib and replace
    fun List<Long>.lcm(): Long {
        var result = this[0]
        this.forEachIndexed { i, _ -> result = lcm(result, this[i]) }
        return result
    }

    private fun lcm(a: Long, b: Long) = a * (b / gcd(a, b))

    private fun gcd(a: Long, b: Long): Long {
        var n1 = a
        var n2 = b
        while (n1 != n2) {
            if (n1 > n2) n1 -= n2 else n2 -= n1
        }
        return n1
    }
}