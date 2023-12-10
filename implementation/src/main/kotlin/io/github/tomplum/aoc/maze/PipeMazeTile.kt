package io.github.tomplum.aoc.maze

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.MapTile

class PipeMazeTile(override val value: Char) : MapTile<Char>(value) {
    fun isVerticalPipe() = value == '|'

    fun isHorizontalPipe() = value == '-'

    fun isNorthEastBend() = value == 'L'

    fun isNorthWestBend() = value == 'J'

    fun isSouthWestBend() = value == '7'

    fun isSouthEastBend() = value == 'F'

    fun isGround() = value == '.'

    fun isStartingPosition() = value == 'S'

    val type = when(value) {
        '|' -> PipeType.VERTICAL
        '-' -> PipeType.HORIZONTAL
        'L' -> PipeType.NORTH_EAST_RIGHT_ANGLE
        'J' -> PipeType.NORTH_WEST_RIGHT_ANGLE
        '7' -> PipeType.SOUTH_WEST_RIGHT_ANGLE
        'F' -> PipeType.SOUTH_EAST_RIGHT_ANGLE
        '.' -> PipeType.GROUND
        'S' -> PipeType.STARTING
        else -> throw IllegalArgumentException("Invalid PipeTile Value [$value]")
    }

    private val northConnections = listOf(PipeType.SOUTH_EAST_RIGHT_ANGLE, PipeType.VERTICAL, PipeType.SOUTH_WEST_RIGHT_ANGLE)
    private val westConnections = listOf(PipeType.HORIZONTAL, PipeType.NORTH_WEST_RIGHT_ANGLE, PipeType.NORTH_EAST_RIGHT_ANGLE, PipeType.SOUTH_EAST_RIGHT_ANGLE, PipeType.SOUTH_WEST_RIGHT_ANGLE)
    private val southConnections = listOf(PipeType.NORTH_EAST_RIGHT_ANGLE, PipeType.VERTICAL, PipeType.NORTH_WEST_RIGHT_ANGLE)
    private val eastConnections = listOf(PipeType.HORIZONTAL, PipeType.NORTH_WEST_RIGHT_ANGLE, PipeType.SOUTH_WEST_RIGHT_ANGLE)

    private val connections = mutableMapOf(
        PipeType.VERTICAL to mutableMapOf(
            Direction.UP to northConnections,
            Direction.DOWN to southConnections,
        ),
        PipeType.HORIZONTAL to mutableMapOf(
            Direction.RIGHT to eastConnections + listOf(PipeType.NORTH_EAST_RIGHT_ANGLE, PipeType.SOUTH_EAST_RIGHT_ANGLE),
            Direction.LEFT to westConnections,
        ),
        PipeType.NORTH_EAST_RIGHT_ANGLE to mutableMapOf(
            Direction.UP to northConnections,
            Direction.RIGHT to eastConnections
        ),
        PipeType.NORTH_WEST_RIGHT_ANGLE to mutableMapOf(
            Direction.UP to northConnections,
            Direction.LEFT to westConnections
        ),
        PipeType.SOUTH_WEST_RIGHT_ANGLE to mutableMapOf(
            Direction.DOWN to southConnections,
            Direction.LEFT to westConnections
        ),
        PipeType.SOUTH_EAST_RIGHT_ANGLE to mutableMapOf(
            Direction.DOWN to southConnections,
            Direction.RIGHT to eastConnections
        )
    )

    fun canConnectTo(other: PipeMazeTile, direction: Direction): Boolean {
        val possible = connections[type]!![direction]
        return possible?.contains(other.type) ?: false
    }
}