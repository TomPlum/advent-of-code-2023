package io.github.tomplum.aoc.map.forest

import io.github.tomplum.libs.math.map.MapTile

class ForestTile(override val value: Char): MapTile<Char>(value) {
    fun isTraversable() = isPath() || isSteepSlope()

    fun isPath() = value == '.'

    fun isForestWall() = value == '#'

    fun isSteepSlope() = value in "^>v<"
}