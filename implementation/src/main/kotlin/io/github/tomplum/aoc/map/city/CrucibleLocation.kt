package io.github.tomplum.aoc.map.city

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

data class CrucibleLocation(
    val position: Point2D,
    val direction: Direction,
    val isMovingStraight: Boolean,
    var consecutiveSteps: Int = 1
)