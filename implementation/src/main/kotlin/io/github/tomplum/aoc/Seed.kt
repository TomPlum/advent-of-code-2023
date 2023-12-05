package io.github.tomplum.aoc

data class SeedAttributeMap(val category: String, val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
    private val sourceRange = sourceRangeStart..<(sourceRangeStart + rangeLength)
    private val destinationRange = destinationRangeStart..<(destinationRangeStart + rangeLength)

    private val categoryParts = category.split(" ")[0].split("-to-")
    val sourceCategory = categoryParts.first()
    val targetCategory = categoryParts.last()

    fun getSourceRange(): LongRange {
        return sourceRangeStart..<(sourceRangeStart + rangeLength)
    }

    fun getDestinationRange(): LongRange {
        return destinationRangeStart..<(destinationRangeStart + rangeLength)
    }

    fun mapSeedNumber(seedNumber: Long): Long? {
        if (seedNumber in sourceRange) {
            val index = sourceRange.indexOf(seedNumber)
            return destinationRange.elementAt(index)
        }

        return null
    }
}