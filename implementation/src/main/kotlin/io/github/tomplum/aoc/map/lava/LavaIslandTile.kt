package io.github.tomplum.aoc.map.lava

import io.github.tomplum.libs.math.map.MapTile

class LavaIslandTile(override val value: Char): MapTile<Char>(value) {
    fun isRock() = value == '#'
    fun isAsh() = value == '.'
}