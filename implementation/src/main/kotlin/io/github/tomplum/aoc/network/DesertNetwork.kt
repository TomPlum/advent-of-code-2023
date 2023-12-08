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
}