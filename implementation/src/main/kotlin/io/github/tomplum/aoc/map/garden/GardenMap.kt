package io.github.tomplum.aoc.map.garden

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class GardenMap(data: List<String>): AdventMap2D<GardenPlot>() {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = GardenPlot(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun traverse(steps: Int): Int {
        val plots = filterTiles { tile -> tile.isStartingPlot() }.map { (pos) -> pos }.toList().toMutableList()
        val seen = mutableSetOf(plots.first())
        var lastQuantitySeen = 0

        repeat(steps) {
            if (plots.isNotEmpty()) {
                val traversableAdj = plots.flatMap { pos -> pos.orthogonallyAdjacent() }
                    //.filterNot { pos -> pos in seen }
                    .map { pos -> pos to getTile(pos, GardenPlot('#')) }
                    .filter { (_, tile) -> tile.isGardenPlot() || tile.isStartingPlot() }
                    .toMap()

                lastQuantitySeen = traversableAdj.size

                plots.clear()
                plots += traversableAdj.keys
                seen += traversableAdj.keys
            }
        }

        return lastQuantitySeen
    }
}