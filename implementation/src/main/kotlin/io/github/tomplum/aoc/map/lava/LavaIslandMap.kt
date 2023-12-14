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

        val columnToLeftOfReflectionLine = columns.scanReflections()
        val rowsAboveReflectionLine = rows.scanReflections()

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
        } else {
            throw IllegalStateException("")
        }
    }

    private fun List<Int>.scanReflections() = this.let {
        val chunks = this.windowed(2).mapIndexed { i, list -> Pair(Pair(i, i + 1), Pair(list[0], list[1])) }
        val centerPoints = chunks.filter { (_, values) -> values.first == values.second }
        centerPoints.map { (indices, _) ->
            var leftIndex = indices.first
            var rightIndex = indices.second
            var isReflectiveToEdge = false
            var isReflective = true

            while(isReflective && leftIndex >= 0 && rightIndex <= this.lastIndex) {
                if (this[leftIndex] == this[rightIndex]) {
                    if (leftIndex == 0 || rightIndex == this.lastIndex) {
                        isReflectiveToEdge = true
                    }

                    leftIndex -= 1
                    rightIndex += 1
                } else {
                    isReflective = false
                }
            }

            if (isReflectiveToEdge) {
                indices.second
            } else null
        }.find { it != null }
    }

    data class PatternAnalysis(val reflectionType: ReflectionType, val value: Int)

    enum class ReflectionType {
        HORIZONTAL, VERTICAL
    }
}