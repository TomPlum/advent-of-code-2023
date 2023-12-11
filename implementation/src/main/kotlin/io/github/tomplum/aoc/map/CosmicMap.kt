package io.github.tomplum.aoc.map

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class CosmicMap(data: List<String>) : AdventMap2D<CosmicTile>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = CosmicTile(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun calculateGalacticDistancesAfterExpansion(horizontalExpansion: Int = 2, verticalExpansion: Int = 2): Int {
        // TODO: Add getRows() and getColumns() to the advent map class

        val xMin = xMin()!!
        val xMax = xMax()!!
        val yMin = yMin()!!
        val yMax = yMax()!!

        val xInsertionPoints = (xMin..xMax).map { x ->
            (yMin..yMax).map { y ->
                Point2D(x, y)
            }
        }.filterNot { column ->
            filterPoints(column.toSet()).any { it.value.isGalaxy() }
        }.map { column -> column.first().x }

        val yInsertionPoints = (yMin..yMax).map { y ->
            (xMin..xMax).map { x ->
                Point2D(x, y)
            }
        }.filterNot { column ->
            filterPoints(column.toSet()).any { it.value.isGalaxy() }
        }.map { column -> column.first().y }

        val galaxies = filterTiles { tile -> tile.isGalaxy() }.keys.toList()

        val xShiftedGalaxies = xInsertionPoints.foldIndexed(galaxies) { i, acc, x ->
            acc.map { pos ->
                if (pos.x > (x + (i * (horizontalExpansion - 1)))) {
                    pos.shift(Direction.RIGHT, horizontalExpansion - 1)
                } else {
                    pos
                }
            }
        }

        val yShiftedGalaxies = yInsertionPoints.foldIndexed(xShiftedGalaxies) { i, acc, y ->
            acc.map { pos ->
                if (pos.y > (y + (i * (verticalExpansion - 1)))) {
                    pos.shift(Direction.UP, verticalExpansion - 1)
                } else {
                    pos
                }
            }
        }

        val seen = mutableListOf<String>()
        val sum = yShiftedGalaxies.sumOf { galaxyPosition ->
            yShiftedGalaxies.filterNot { pos -> pos == galaxyPosition || "$pos$galaxyPosition" in seen }.sumOf { targetGalaxy ->
                seen.add("$galaxyPosition$targetGalaxy")
                seen.add("$targetGalaxy$galaxyPosition")
                galaxyPosition.distanceBetween(targetGalaxy)
            }
        }

        return sum
    }
}