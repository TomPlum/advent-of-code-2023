package io.github.tomplum.aoc.network.strategy

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