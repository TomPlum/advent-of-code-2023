package io.github.tomplum.aoc

data class SeedAttributeMap(val category: String, val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {

    private val categoryParts = category.split(" ")[0].split("-to-")
    val sourceCategory = categoryParts.first()
    val targetCategory = categoryParts.last()

    fun mapSeedNumber(seedNumber: Long): Long? {
        if (seedNumber >= sourceRangeStart && seedNumber < (sourceRangeStart + rangeLength)) {
            return destinationRangeStart + (seedNumber - sourceRangeStart)
        }

        return null
    }
}