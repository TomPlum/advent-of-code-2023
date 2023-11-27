package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.ExampleImplementation
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

// Set the generic types to the return types for parts 1 and 2 respectively
class Day1 : Solution<Int, Int> {

    // Make sure to add a package in resources called "day{number}" with input.txt in it
    private val input = InputReader.read<Int>(Day(1)).value

    private val solver = ExampleImplementation()

    override fun part1(): Int {
        // Replace with your implementation that returns the answer to part 1
        return solver.solveSomeProblem()
    }

    override fun part2(): Int {
        // Replace with your implementation that returns the answer to part 2
        return solver.solveSomeProblem()
    }
}
