package io.github.tomplum.aoc.network

import io.github.tomplum.aoc.network.strategy.NetworkNavigationStrategy

class DesertNetwork {
    fun stepsRequiredToReachEnd(strategy: NetworkNavigationStrategy): Long {
        return strategy.navigate()
    }

}