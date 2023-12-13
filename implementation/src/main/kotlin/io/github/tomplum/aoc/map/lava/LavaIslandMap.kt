package io.github.tomplum.aoc.map.lava

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class LavaIslandMap(data: List<String>): AdventMap2D<LavaIslandTile>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = LavaIslandTile(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    private fun String.parseAsBinaryString(): Int = Integer.parseInt(this, 2)

    fun findInflectionPoints(): PatternAnalysis {
        val columns = (xMin()!!..xMax()!!).map { x ->
            (yMin()!!..yMax()!!).map { y ->
                getTile(Point2D(x, y))
            }
        }.map { values ->
            values.map { tile -> if (tile.isRock()) 1 else 0 }.joinToString("").parseAsBinaryString()
        }

        val rows = (yMin()!!..yMax()!!).map { y ->
            (xMin()!!..xMax()!!).map { x ->
                getTile(Point2D(x, y))
            }
        }.map { values ->
            values.map { tile -> if (tile.isRock()) 1 else 0 }.joinToString("").parseAsBinaryString()
        }

        val columnToLeftOfReflectionLine = columns.let {
            val chunks = columns.windowed(2)
            val s = chunks.find { (a, b) -> a == b }
            if (s != null) {
                chunks.indexOf(s) + 1
            } else {
                null
            }
        }

        val rowsAboveReflectionLine = rows.let {
            val chunks = rows.windowed(2)
            val s = chunks.find { (a, b) -> a == b }
            if (s != null) {
                chunks.indexOf(s) + 1
            } else {
                null
            }
        }

        if (rowsAboveReflectionLine != null && columnToLeftOfReflectionLine != null) {
            return if (rowsAboveReflectionLine > columnToLeftOfReflectionLine) {
                PatternAnalysis(ReflectionType.HORIZONTAL, rowsAboveReflectionLine)
            } else {
                PatternAnalysis(ReflectionType.VERTICAL, columnToLeftOfReflectionLine)
            }
        }

        if (columnToLeftOfReflectionLine == null && rowsAboveReflectionLine != null) {
            return PatternAnalysis(ReflectionType.HORIZONTAL, rowsAboveReflectionLine)
        }

        if (columnToLeftOfReflectionLine != null) {
            return PatternAnalysis(ReflectionType.VERTICAL, columnToLeftOfReflectionLine)
        } else throw IllegalStateException("")
    }

    data class PatternAnalysis(val reflectionType: ReflectionType, val value: Int)

    enum class ReflectionType {
        HORIZONTAL, VERTICAL
    }
}