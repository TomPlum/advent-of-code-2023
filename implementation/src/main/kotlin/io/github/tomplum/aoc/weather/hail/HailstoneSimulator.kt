package io.github.tomplum.aoc.weather.hail

import kotlin.math.sign

// TODO: Shall we add these other types to lib?
data class DoublePoint2D(val x: Double, val y: Double)
data class LongPoint3D(val x: Long, val y: Long, val z: Long)

data class Line(val a: DoublePoint2D, val b: DoublePoint2D, val velocity: DoublePoint2D) {
    fun intersectWith(other: Line): DoublePoint2D {
        val (a1, b1, c1) = this.linearEquationVariables()
        val (a2, b2, c2) = other.linearEquationVariables()

        val determinant = (a1 * b2) - (a2 * b1)

        return if (determinant == 0.0) {
            DoublePoint2D(Double.MAX_VALUE, Double.MAX_VALUE)
        } else {
            val x = ((b2 * c1) - (b1 * c2)) / determinant
            val y = ((a1 * c2) - (a2 * c1)) / determinant
            DoublePoint2D(x, y)
        }
    }

    private fun linearEquationVariables(): Triple<Double, Double, Double> {
        val a1 = b.y - a.y
        val b1 = a.x - b.x
        val c1 = a1 * (a.x) + b1 * (a.y)
        return Triple(a1, b1, c1)
    }
}

class HailstoneSimulator(private val data: List<String>) {
    fun simulate(xTestArea: Long, yTestArea: Long): Int {
        val hailstoneData = data.map { line ->
            val (coords, velocity) = line.split("@").map { result -> result.trim() }
            val start = coords.split(", ").map { value -> value.toDouble() }.let { (x, y, z) -> DoublePoint2D(x, y) }
            val initialVelocity = velocity.split(", ").map { value -> value.replace(" ", "").toDouble() }.let { (x, y, z) -> Triple(x, y, z) }
            start to initialVelocity
        }

        val lines = hailstoneData.map { (pos, velocity) ->
            val otherPoint = DoublePoint2D(pos.x + velocity.first, pos.y + velocity.second)
            Line(DoublePoint2D(pos.x, pos.y), otherPoint, DoublePoint2D(velocity.first, velocity.second))
        }

        val testAreaRange = xTestArea.toDouble()..yTestArea.toDouble()

        return lines.distinctPairs().count { (a, b) ->
            val intersect = a.intersectWith(b)
            val firstInFuture = (intersect.x - a.a.x).sign == a.velocity.x.sign && (intersect.y - a.a.y).sign == a.velocity.y.sign
            val secondInFuture = (intersect.x - b.a.x).sign == b.velocity.x.sign && (intersect.y - b.a.y).sign == b.velocity.y.sign
            val isFuture = firstInFuture && secondInFuture
            val inRange = intersect.x in testAreaRange && intersect.y in testAreaRange
            inRange && isFuture
        }
    }

    fun throwStone(): Long {
        return z3 {
            val x_t = int("x_t")
            val y_t = int("y_t")
            val z_t = int("z_t")

            val xvel_t = int("xvel_t")
            val yvel_t = int("yvel_t")
            val zvel_t = int("zvel_t")

            val dt1 = int("dt1")
            val dt2 = int("dt2")
            val dt3 = int("dt3")

            val dt = listOf(dt1, dt2, dt3)

            val hailstoneData = data.map { line ->
                val (coords, velocity) = line.split("@").map { result -> result.trim() }
                val start = coords.split(", ").map { value -> value.toLong() }.let { (x, y, z) -> LongPoint3D(x, y, z) }
                val initialVelocity = velocity.split(", ").map { value -> value.replace(" ", "").toLong() }.let { (x, y, z) -> Triple(x, y, z) }
                start to initialVelocity
            }

            val eqs = hailstoneData.take(3).flatMapIndexed { idx, (ball, velocity) ->
                listOf(
                    (x_t - ball.x) eq (dt[idx] * (velocity.first - xvel_t)),
                    (y_t - ball.y) eq (dt[idx] * (velocity.second - yvel_t)),
                    (z_t - ball.z) eq (dt[idx] * (velocity.third - zvel_t)),
                )
            }

            solve(eqs)

            eval(x_t + y_t + z_t).toLong()
        }
    }

    // TODO: Replace from libs ones released
    fun <T> Collection<T>.distinctPairs(): List<Pair<T, T>> = this.flatMapIndexed { i, element ->
        this.drop(i + 1).map { otherElement ->
            element to otherElement
        }
    }
}