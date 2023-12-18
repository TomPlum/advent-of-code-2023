package io.github.tomplum.aoc.map.lagoon

import io.github.tomplum.libs.math.map.MapTile

class LagoonTile(override val value: Char): MapTile<Char>(value) {
    fun isTrench() = value == '#'

    fun isGroundLevelTerrain() = value == '.'
}