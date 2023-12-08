package io.github.tomplum.aoc.network.strategy

import io.github.tomplum.libs.extensions.lcm

class EtherealNavigation(documents: List<String>) : NetworkNavigationStrategy(documents) {
    override fun navigate(): Long {
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
}