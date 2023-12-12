package io.github.tomplum.aoc.onsen

class SpringAnalyser(private val records: List<String>) {
    fun calculateArrangements(copies: Int = 1): Long = records.sumOf { record ->
        val (springs, groups) = record.trim().split(" ")
        val unfoldedSprings = "$springs?".repeat(copies).dropLast(1)
        val damagedGroups = "$groups,".repeat(copies).dropLast(1).split(",").map(String::toInt)
        countArrangements(unfoldedSprings, damagedGroups)
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
}