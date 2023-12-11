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

    fun calculateGalacticDistancesAfterExpansion(): Int {
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

        xInsertionPoints.forEachIndexed { i, x ->
            filterTiles { tile -> tile.isGalaxy() }
                .filter { (pos) -> pos.x > (x + i) }
                .forEach { (pos) ->
                    removeTile(pos)
                    addTile(pos, CosmicTile('.'))
                    addTile(pos.shift(Direction.RIGHT), CosmicTile('#'))
                }
        }

        yInsertionPoints.forEachIndexed { i, y ->
            filterTiles { tile -> tile.isGalaxy() }
                .filter { (pos) -> pos.y > (y + i) }
                .forEach { (pos) ->
                    removeTile(pos)
                    addTile(pos, CosmicTile('.'))
                    addTile(pos.shift(Direction.UP), CosmicTile('#'))
                }
        }

        val seen = mutableListOf<String>()
        val galaxies = filterTiles { tile -> tile.isGalaxy() }.keys.toList()
        val sum = galaxies.sumOf { galaxyPosition ->
            galaxies.filterNot { pos -> pos == galaxyPosition || "$pos$galaxyPosition" in seen }.sumOf { targetGalaxy ->
                seen.add("$galaxyPosition$targetGalaxy")
                seen.add("$targetGalaxy$galaxyPosition")
                galaxyPosition.distanceBetween(targetGalaxy)
            }
        }

        return sum
    }
}