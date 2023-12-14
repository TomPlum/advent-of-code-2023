package io.github.tomplum.aoc.map.platform

import io.github.tomplum.libs.math.map.MapTile

class PlatformTile(override val value: Char): MapTile<Char>(value) {
    fun isRoundedRock() = value == 'O'

    fun isCubicRock() = value == '#'

    fun isEmptySpace() = value == '.'
}