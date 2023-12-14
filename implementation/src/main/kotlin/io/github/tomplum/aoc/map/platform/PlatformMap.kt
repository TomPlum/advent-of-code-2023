package io.github.tomplum.aoc.map.platform

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class PlatformMap(data: List<String>): AdventMap2D<PlatformTile>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = PlatformTile(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun calculateNorthSupportBeamsTotalLoad(): Int {
        var northernMostAvailableY = 0

        (xMin()!!..xMax()!!).forEach { x ->
            (yMin()!!..yMax()!!).forEach { y ->
                val position = Point2D(x, y)
                val tile = getTile(position)

                if (tile.isEmptySpace()) {
                    northernMostAvailableY = position.y
                }

                if (tile.isCubicRock()) {
                    northernMostAvailableY + position.y + 1
                }

                if (tile.isRoundedRock()) {
                    val oldRock = removeTile(position)
                    addTile(position, PlatformTile('.'))
                    addTile(Point2D(position.x, getNorthernMostAvailablePlatform(x, y)), oldRock!!)

                    northernMostAvailableY += 1
                }
            }
        }

        return filterTiles { tile -> tile.isRoundedRock() }.keys.sumOf { pos ->
            (yMax()!! + 1) - pos.y
        }
    }

    private fun getNorthernMostAvailablePlatform(x: Int, yRock: Int): Int {
        val column = getDataMap().filterKeys { pos -> pos.x == x }.entries.sortedByDescending { (pos) -> pos.y }
        var northernMostEmpty = Int.MAX_VALUE

        column.forEach { (pos, tile) ->
            if (tile.isCubicRock() && pos.y < yRock) {
                return northernMostEmpty
            }

            if (tile.isEmptySpace()) {
                northernMostEmpty = pos.y
            }
        }

        return northernMostEmpty
    }
}