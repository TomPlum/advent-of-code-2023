package io.github.tomplum.aoc.sensor

class Oasis(private val report: List<String>) {
    fun extrapolateForwards(): Int = report.map { line ->
        val values = line.trim().split(" ").map { value -> value.toInt() }
        val extrapolated = extrapolateHistory(values) { (a, b) -> b - a }.reversed().toMutableList()
        extrapolated.add(values)
        extrapolated
    }.sumOf { sequences -> sequences
        .map { value -> value.last() }
        .fold(0) { extrapolated, value -> value + extrapolated }.toInt()
    }

    fun extrapolateBackwards(): Int = report.map { line ->
        val values = line.trim().split(" ").map { value -> value.toInt() }.reversed()
        val extrapolated = extrapolateHistory(values){ (a, b) -> a - b }.reversed().toMutableList()
        extrapolated.add(values)
        extrapolated
    }.sumOf { sequences -> sequences
        .map { value -> value.last() }
        .fold(0) { extrapolated, value -> value - extrapolated }.toInt()
    }

    private fun extrapolateHistory(values: List<Int>, comparator: (values: List<Int>) -> Int): MutableList<List<Int>> {
        val result = mutableListOf<List<Int>>()

        if (values.any { value -> value != 0 }) {
            val deltas = values.windowed(2, transform =  comparator)
            result.add(deltas)

            val extrapolated = extrapolateHistory(deltas, comparator)
            if (extrapolated.isNotEmpty()) {
                result += extrapolated
            }
        }

        return result
    }
}