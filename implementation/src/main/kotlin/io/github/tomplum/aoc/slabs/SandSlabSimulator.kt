package io.github.tomplum.aoc.slabs

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D
import io.github.tomplum.libs.math.point.Point3D

class SandSlabSimulator(snapshot: List<String>) {

    private val bricks = snapshot
        .mapIndexed { index, line -> Brick.from(index, line) }
        .sortedBy { brick -> brick.zRange.first }

    private val supports = mutableMapOf<Int, MutableSet<Int>>()
    private val supported = mutableMapOf<Int, MutableSet<Int>>()

    init {
        val maxes = mutableMapOf<Point2D, Pair<Int, Int>>().withDefault { -1 to 0 }

        for (brick in bricks) {
            val points = brick.getSecondDimension()
            val zMax = points.map { pos -> maxes.getValue(pos) }.maxOf { it.second }
            brick.zRange = zMax + 1..<zMax + 1 + brick.zRange.last - brick.zRange.first + 1

            for (point in points) {
                val (id, z) = maxes.getValue(point)

                if (z == zMax && id != -1) {
                    supports.getOrPut(id) { mutableSetOf() }.add(brick.id)
                    supported.getOrPut(brick.id) { mutableSetOf() }.add(id)
                }

                maxes[point] = brick.id to brick.zRange.last
            }
        }
    }

    fun calculateDisintegratableBricks(): Int {
        val nonDisintegrated = supports.count { (_, others) -> others.any { other -> supported.getValue(other).size == 1 } }
        return bricks.size - nonDisintegrated
    }

    fun simulateChainReaction(): Int = bricks.sumOf { brick ->
        val falling = mutableSetOf(brick.id)
        var nextBricks = supports.getOrDefault(brick.id, emptySet())

        while (nextBricks.isNotEmpty()) {
            nextBricks = buildSet {
                nextBricks.forEach { next ->
                    if ((supported.getValue(next) - falling).isEmpty()) {
                        falling += next
                    }

                    addAll(supports.getOrDefault(next, emptySet()))
                }
            }
        }

        falling.size - 1
    }

    // TODO: Move to libs
    private fun Point3D.zShift(direction: Direction): Point3D = when(direction) {
        Direction.UP -> Point3D(this.x, this.y, this.z + 1)
        Direction.DOWN -> Point3D(this.x, this.y, this.z - 1)
        else -> throw IllegalArgumentException("Cannot shift a 3D point in the z-axis in the $direction direction.")
    }

    private data class Brick(val id: Int, val xRange: IntRange, val yRange: IntRange, var zRange: IntRange) {
        companion object {
            fun from(index: Int, line: String): Brick {
                val (start, end) = line.split("~")
                val (x1, y1, z1) = start.split(",").map { value -> value.toInt() }
                val (x2, y2, z2) = end.split(",").map { value -> value.toInt() }
                return Brick(index, x1..x2, y1..y2, z1..z2)
            }
        }

        fun getSecondDimension() = xRange.flatMap { x -> yRange.map { y -> Point2D(x, y) } }
    }
}