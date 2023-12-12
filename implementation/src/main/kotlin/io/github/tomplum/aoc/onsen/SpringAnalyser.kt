package io.github.tomplum.aoc.onsen

class SpringAnalyser(private val records: List<String>) {
    fun calculateArrangements(): Long {
        /*records.forEach { record ->
            val (springs, groups) = record.trim().split(" ")
            val damagedGroups = groups.split(",").map { value -> value.toInt() }
            val map = springs.mapIndexed { i, spring -> i to spring }.toMap().toMutableMap()

            damagedGroups.forEach { groupSize ->
                var damagedAccountedFor = 0
                var currentWindowSize = 0
                var lastWasGroupEnd = false

                springs.forEachIndexed { i, spring ->
                    currentWindowSize++

                    if (lastWasGroupEnd) {
                        if (spring.isUnknown()) {
                            currentWindowSize = 0
                            lastWasGroupEnd = false
                            map[i] = '.'
                        }
                    }

                    if (spring.isUnknown() && currentWindowSize == 1) {
                        if (currentWindowSize == groupSize) {
                            map[i] = '#'
                            currentWindowSize = 0
                            damagedAccountedFor = 0
                            lastWasGroupEnd = true
                        }
                    }

                    if (spring.isDamaged()) {
                        if (damagedAccountedFor == currentWindowSize)
                        damagedAccountedFor++
                    }
                }
            }
        }*/
        return records.sumOf { record ->
            val (springs, groups) = record.trim().split(" ")
            val damagedGroups = groups.split(",").map(String::toInt)
            countArrangements(springs, damagedGroups)
        }
    }

    private val cache = hashMapOf<Pair<String, List<Int>>, Long>()

    private fun countArrangements(config: String, groups: List<Int>): Long {
        if (groups.isEmpty()) {
            return if ("#" in config) 0 else 1
        }

        if (config.isEmpty()) {
            return 0
        }

        return cache.getOrPut(config to groups) {
            var result = 0L

            if (config.first() in ".?")  {
                result += countArrangements(config.drop(1), groups)
            }

            val damagedOrUnknown = config.first() in "#?"
            val firstGroupLessThanConfigLength = groups.first() <= config.length
            val noOperationalSpringsInFirstGroup = "." !in config.take(groups.first())

            if (damagedOrUnknown &&
                firstGroupLessThanConfigLength &&
                noOperationalSpringsInFirstGroup &&
                (groups.first() == config.length || config[groups.first()] != '#')
            ) {
                result += countArrangements(config.drop(groups.first() + 1), groups.drop(1))
            }

            result
        }
    }

    private fun Char.isDamaged() = this == '#'
    private fun Char.isUnknown() = this == '?'
    private fun Char.isOperational() = this == '.'
}