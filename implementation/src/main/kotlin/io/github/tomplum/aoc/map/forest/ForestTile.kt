package io.github.tomplum.aoc.map.forest

import io.github.tomplum.libs.math.map.MapTile

class ForestTile(override val value: Char): MapTile<Char>(value) {
    fun isStartingPoint() = value == 'S'

    fun isTraversablePath() = value == '.' || isSteepSlope()

    fun isForestWall() = value == '#'

    fun isSteepSlope() = value in "^>v<"
}