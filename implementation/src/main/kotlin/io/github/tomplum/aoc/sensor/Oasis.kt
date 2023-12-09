package io.github.tomplum.aoc.sensor

class Oasis(private val report: List<String>) {
    fun extrapolateFromHistory(): Int {
        val sum = report.map { line ->
            val values = line.trim().split(" ").map { value -> value.toInt() }
            val extrapolated = extrapolateHistory(values).reversed().toMutableList()
            extrapolated.add(values)
            extrapolated
        }.sumOf { sequences ->
            val last = sequences.map { it.last() }
            var currentValue = 0
            last.forEach { value ->
                if (value != 0) {
                    currentValue += value
                }
            }
            currentValue
        }
        return sum
    }

    private fun extrapolateHistory(values: List<Int>): MutableList<List<Int>> {
        val result = mutableListOf<List<Int>>()

        if (values.any { value -> value != 0 }) {
            val deltas = values.windowed(2) { (a, b) -> b - a }
            result.add(deltas)

            val extrapolated = extrapolateHistory(deltas)
            if (extrapolated.isNotEmpty()) {
                result += extrapolated
            }
        }

        return result
    }
}