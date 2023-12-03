package io.github.tomplum.aoc.engine

import io.github.tomplum.libs.math.map.MapTile

class EnginePart(override val value: Char) : MapTile<Char>(value) {
    fun isSymbol(): Boolean {
        return !value.isDigit() && value != '.'
    }

    fun isGearCandidate(): Boolean {
        return value == '*'
    }
    
    fun isIntegerValue(): Boolean {
        return value.isDigit()
    }
}