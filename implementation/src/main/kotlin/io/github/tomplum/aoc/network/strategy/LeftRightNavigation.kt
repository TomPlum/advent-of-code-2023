package io.github.tomplum.aoc.network.strategy

class LeftRightNavigation(documents: List<String>) : NetworkNavigationStrategy(documents) {
    override fun navigate(): Long {
        var currentPosition = "AAA"
        var stepsTaken = 0L

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