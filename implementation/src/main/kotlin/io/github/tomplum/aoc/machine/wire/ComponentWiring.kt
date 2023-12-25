package io.github.tomplum.aoc.machine.wire

import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleWeightedGraph

class ComponentWiring(data: List<String>) {

    private val components = data.associate { line ->
        val (name, connections) = line.split(": ")
        name to connections.split(" ")
    }

    fun disconnectWires(): Int {
        val graph = SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)

        components.forEach { (name, others) ->
            graph.addVertex(name)

            others.forEach { other ->
                graph.addVertex(other)
                graph.addEdge(name, other)
            }
        }

        val oneSide = StoerWagnerMinimumCut(graph).minCut()

        return (graph.vertexSet().size - oneSide.size) * oneSide.size
    }
}