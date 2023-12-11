package io.github.tomplum.aoc.map

import io.github.tomplum.libs.math.map.MapTile

class CosmicTile(override val value: Char): MapTile<Char>(value) {
    fun isGalaxy() = value == '#'

    fun isEmptySpace() = value == '.'
}