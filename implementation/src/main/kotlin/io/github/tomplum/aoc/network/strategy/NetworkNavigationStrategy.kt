package io.github.tomplum.aoc.network.strategy

abstract class NetworkNavigationStrategy(documents: List<String>) {
    protected val directions = documents.first().trim().map { it }

    protected val nodes = documents.drop(2).associate { line ->
        val info = line.filterNot { it == ' ' }
        val (node, destinations) = info.split("=")
        val (left, right) = destinations.removePrefix("(").removeSuffix(")").split(",")
        node to Pair(left, right)
    }

    abstract fun navigate(): Long
}