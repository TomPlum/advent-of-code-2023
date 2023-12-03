package io.github.tomplum.aoc

import io.github.tomplum.libs.math.map.MapTile

class EnginePart(override val value: Char) : MapTile<Char>(value) {
    fun isSymbol(): Boolean {
        return value in listOf('*', '+', '$', '#')
    }
    
    fun isIntegerValue(): Boolean {
        return value.isDigit()
    }
}