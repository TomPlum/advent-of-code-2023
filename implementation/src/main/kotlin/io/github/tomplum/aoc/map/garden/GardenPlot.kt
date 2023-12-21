package io.github.tomplum.aoc.map.garden

import io.github.tomplum.libs.math.map.MapTile

class GardenPlot(override val value: Char): MapTile<Char>(value) {
    fun isStartingPlot() = value == 'S'

    fun isGardenPlot() = value == '.'

    fun isRock() = value == '#'
}