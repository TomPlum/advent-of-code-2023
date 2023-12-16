package io.github.tomplum.aoc.map.light

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.MapTile

class ContraptionTile(override val value: Char): MapTile<Char>(value) {
    var isEnergised = false

    fun isEmpty() = value == '.'

    fun isMirror() = value in "\\/"

    fun isSplitter() = value in "-|"

    fun reflectionDirection(propagatedFrom: Direction) = when(propagatedFrom) {
        Direction.LEFT -> when(value) {
            '/' -> Direction.UP
            '\\' -> Direction.DOWN
            else -> throw IllegalArgumentException("Tile is not a mirror cannot reflect light.")
        }
        Direction.RIGHT -> when(value) {
            '/' -> Direction.DOWN
            '\\' -> Direction.UP
            else -> throw IllegalArgumentException("Tile is not a mirror cannot reflect light.")
        }
        Direction.DOWN -> when(value) {
            '/' -> Direction.RIGHT
            '\\' -> Direction.LEFT
            else -> throw IllegalArgumentException("Tile is not a mirror cannot reflect light.")
        }
        Direction.UP -> when(value) {
            '/' -> Direction.LEFT
            '\\' -> Direction.RIGHT
            else -> throw IllegalArgumentException("Tile is not a mirror cannot reflect light.")
        }
        else -> throw IllegalArgumentException("Mirrors cannot reflection light when the beam came from the $propagatedFrom")
    }

    fun splitDirections(propagatedFrom: Direction) = when(propagatedFrom) {
        Direction.UP -> when(value) {
            '|' -> listOf(Direction.UP)
            '-' -> listOf(Direction.LEFT, Direction.RIGHT)
            else -> throw IllegalArgumentException("Tile is not a splitter.")
        }
        Direction.DOWN -> when(value) {
            '|' -> listOf(Direction.DOWN)
            '-' -> listOf(Direction.LEFT, Direction.RIGHT)
            else -> throw IllegalArgumentException("Tile is not a splitter.")
        }
        Direction.LEFT -> when(value) {
            '|' -> listOf(Direction.UP, Direction.DOWN)
            '-' -> listOf(Direction.LEFT)
            else -> throw IllegalArgumentException("Tile is not a splitter.")
        }
        Direction.RIGHT -> when(value) {
            '|' -> listOf(Direction.UP, Direction.DOWN)
            '-' -> listOf(Direction.RIGHT)
            else -> throw IllegalArgumentException("Tile is not a splitter.")
        }
        else -> throw IllegalArgumentException("Splitter cannot handle light coming from $propagatedFrom")
    }
}