package io.github.tomplum.aoc.weather.hail

data class DoublePoint2D(val x: Double, val y: Double)

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
    fun simulate(xTestArea: Int, yTestArea: Int): Int {
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
            val xDelta = a.a.x - intersect.x
            val yDelta = a.a.y - intersect.y
            val xFuture = (a.velocity.x > 0 && xDelta > 0) || (a.velocity.x < 0 && xDelta < 0)
            val yFuture = (a.velocity.y > 0 && yDelta > 0) || (a.velocity.y < 0 && yDelta < 0)
            val isFuture = xFuture && yFuture
            val inRange = intersect.x in testAreaRange && intersect.y in testAreaRange
            inRange && isFuture
        }
    }

    // TODO: Replace from libs ones released
    fun <T> Collection<T>.distinctPairs(): List<Pair<T, T>> = this.flatMapIndexed { i, element ->
        this.drop(i + 1).map { otherElement ->
            element to otherElement
        }
    }
}