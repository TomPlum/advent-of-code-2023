package io.github.tomplum.aoc

class Almanac(private val data: List<String>) {
    fun findLowestLocationNumber(): Long {
        val seedTypes = data.first().removePrefix("seeds: ").trim().split(" ").map { value -> value.toLong() }

        val mapData = mutableListOf<List<String>>()
        var currentMap = mutableListOf<String>()
        data.drop(2).mapIndexed { i, line ->
            if (i == data.drop(2).lastIndex) {
                currentMap.add(line)
                mapData.add(currentMap)
            } else if (line.isBlank()) {
                mapData.add(currentMap)
                currentMap = mutableListOf()
            } else {
                currentMap.add(line)
            }
        }

        val categoryMaps = mapData.map { data ->
            val category = data.first().trim()
            data.drop(1).map { values ->
                val numbers = values.trim().split(" ").map { value -> value.toLong() }
                SeedAttributeMap(category, numbers[0], numbers[1], numbers[2])
            }
        }

        val seedMap = mutableMapOf<Long, MutableMap<String, Long>>()

        categoryMaps.forEach { categoryMap ->
            val currentMap = mutableMapOf<Long, Long>()

            seedTypes.forEach { seedNumber ->
                val sourceCategory = categoryMap.first().sourceCategory
                val mappedValue = seedMap.getOrDefault(seedNumber, mutableMapOf()).getOrDefault(sourceCategory, seedNumber)

                val candidates = categoryMap.map { map ->
                    map.mapSeedNumber(mappedValue)
                }.filterNot { it == null }

                val number: Long = if (candidates.isEmpty()) mappedValue else candidates.first()!!
                currentMap[seedNumber] = number
            }

            currentMap.forEach { (seedNumber, mapping) ->
                if (seedMap.containsKey(seedNumber)) {
                    val innerMap = seedMap[seedNumber]
                    innerMap?.put(categoryMap.first().targetCategory, mapping)
                } else {
                    seedMap[seedNumber] = mutableMapOf(Pair(categoryMap.first().targetCategory, mapping))
                }
            }
        }

        return seedMap.map { it.value }.minOf { it["location"] ?: 0 }
    }
}