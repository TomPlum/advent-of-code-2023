package io.github.tomplum.aoc

import io.github.tomplum.libs.math.point.Point2D

class ExampleImplementation {
    fun solveSomeProblem(): Int {
        // Using the Point2D class from the AOC Libs to test github packages auth
        val point = Point2D(0, 2)
        val yAxisDistance = point.distanceFromAxisY()
        return 12 * yAxisDistance
    }
}