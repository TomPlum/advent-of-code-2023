package io.github.tomplum.aoc

data class SeedAttributeMap(val category: String, val destinationRangeStart: Int, val sourceRangeStart: Int, val rangeLength: Int) {
    private val sourceRange = sourceRangeStart..<(sourceRangeStart + rangeLength)
    private val destinationRange = destinationRangeStart..<(destinationRangeStart + rangeLength)

    private val categoryParts = category.split(" ")[0].split("-to-")
    val sourceCategory = categoryParts.first()
    val targetCategory = categoryParts.last()

    fun getSourceRange(): IntRange {
        return sourceRangeStart..<(sourceRangeStart + rangeLength)
    }

    fun getDestinationRange(): IntRange {
        return destinationRangeStart..<(destinationRangeStart + rangeLength)
    }

    fun mapSeedNumber(seedNumber: Int): Int? {
        if (seedNumber in sourceRange) {
            val index = sourceRange.indexOf(seedNumber)
            return destinationRange.elementAt(index)
        }

        return null
    }
}