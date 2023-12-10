package io.github.tomplum.aoc.sensor

class Oasis(private val report: List<String>) {
    fun extrapolateForwards(): Int = report
        .map { line -> line.getExtrapolatedHistoricalValues { (a, b) -> b - a } }
        .sumOf { sequences -> sequences
            .map { value -> value.last() }
            .fold(0) { extrapolated, value -> value + extrapolated }.toInt()
        }

    fun extrapolateBackwards(): Int = report
        .map { line -> line.getExtrapolatedHistoricalValues { (a, b) -> a - b } }
        .sumOf { sequences -> sequences
            .map { value -> value.last() }
            .fold(0) { extrapolated, value -> value - extrapolated }.toInt()
        }

    private fun String.getExtrapolatedHistoricalValues(comparator: (values: List<Int>) -> Int): List<List<Int>> {
        val values = this.trim().split(" ").map { value -> value.toInt() }.reversed()
        val extrapolated = extrapolateHistory(values, comparator).reversed().toMutableList()
        extrapolated.add(values)
        return extrapolated
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