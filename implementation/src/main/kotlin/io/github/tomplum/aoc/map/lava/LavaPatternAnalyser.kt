package io.github.tomplum.aoc.map.lava

class LavaPatternAnalyser(data: List<String>) {
    private val lavaIslandMaps = data
        .split { it.isBlank() }
        .map { mapData -> LavaIslandMap(mapData.toList()) }

    fun summariseNotes(): Int =  lavaIslandMaps.map { map -> map.findInflectionPoints() }
        .groupBy { analysis -> analysis.reflectionType }
        .let {
            val columns = it[LavaIslandMap.ReflectionType.VERTICAL]!!.sumOf { it.value }
            val rows = it[LavaIslandMap.ReflectionType.HORIZONTAL]!!.sumOf { it.value }
            return columns + (100 * rows)
        }

    // TODO: Move to libs
    private fun <T> Collection<T>.split(predicate: (element: T) -> Boolean): Collection<Collection<T>> {
        var i = 0
        val data = mutableMapOf<Int, List<T>>()
        var current = mutableListOf<T>()

        this.forEachIndexed { index, element ->
            if (index == this.size - 1) {
                current.add(element)
                data[i] = current
            } else if (predicate(element)) {
                data[i] = current
                i++
                current = mutableListOf()
            } else {
                current.add(element)
            }
        }

        return data.values.toList()
    }
}