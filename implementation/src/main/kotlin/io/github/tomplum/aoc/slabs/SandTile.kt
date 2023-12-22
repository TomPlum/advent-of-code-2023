package io.github.tomplum.aoc.slabs

import io.github.tomplum.libs.math.map.MapTile

class SandTile(val id: Int): MapTile<Int>(id) {
    fun isEmpty() = id == 0
}