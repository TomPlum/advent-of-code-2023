package io.github.tomplum.aoc.slabs

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap3D
import io.github.tomplum.libs.math.point.Point3D

class SandSlabSimulator(snapshot: List<String>): AdventMap3D<SandTile>() {

    private val points = mutableMapOf<Int, List<Point3D>>()

    init {
        snapshot
            .map { line -> line.split("~") }
            .map { (start, end) ->
                val from = start.split(",").let { (x, y, z) -> Point3D(x.toInt(), y.toInt(), z.toInt()) }
                val to = end.split(",").let { (x, y, z) -> Point3D(x.toInt(), y.toInt(), z.toInt()) }

                if (from.x != to.x) {
                    (from.x..to.x).map { xNew ->
                        Point3D(xNew, from.y,  from.z)
                    }
                } else if (from.y != to.y) {
                    (from.y..to.y).map { yNew ->
                        Point3D(from.x, yNew,  from.z)
                    }
                } else if (from.z != to.z) {
                    (from.z..to.z).map { zNew ->
                        Point3D(from.x, from.y,  zNew)
                    }
                } else {
                    throw IllegalArgumentException("Cannot find differing ordinate between from [$from] and to [$to]")
                }
            }.forEachIndexed { index, slabPoints ->
                points[index + 1] = slabPoints

                slabPoints.forEach { point ->
                    addTile(point, SandTile(index + 1))
                }
            }
    }

    fun calculateDisintegratableBricks(): Int {
        points.entries.sortedBy { (_, positions) -> positions.first().z }.forEach { (slabId, slabPoints) ->
            if (slabPoints.none { point -> point.z == 1 }) {
                var canFall = true
                var currentSlabPositions = slabPoints

                while(canFall) {
                    val nextPositions = currentSlabPositions.map { point -> point.zShift(Direction.DOWN) }
                    val isHorizontalBrick = currentSlabPositions.map { pos -> pos.z }.distinct().size == 1

                    val canMove = if (isHorizontalBrick) {
                        nextPositions.none { pos -> hasRecorded(pos) }
                    } else {
                        !hasRecorded(nextPositions.minBy { pos -> pos.z })
                    }

                    if (canMove) {
                        currentSlabPositions.forEach { oldPos -> removeTile(oldPos) }
                        nextPositions.forEach { newPos -> addTile(newPos, SandTile(slabId)) }
                        currentSlabPositions = nextPositions
                    } else {
                        canFall = false
                    }
                }
            }
        }

        val brickPositions = getDataMap().entries.groupBy { it.value.id }
        val count = brickPositions.count { (id, currentBricks) ->
            val above = currentBricks.map { it.key }.map { it.zShift(Direction.UP) }
            val tilesAbove = above.map { getTile(it, SandTile(0)) }.filterNot { it.isEmpty() }

            val isCurrentBrickHorizontal = currentBricks.map { (pos) -> pos.z }.distinct().size == 1

            val isSupportingAnotherBrick = if (isCurrentBrickHorizontal) {
                tilesAbove.isNotEmpty()
            } else {
                hasRecorded(above.maxBy { pos -> pos.z })
            }

            if (isSupportingAnotherBrick) {
                tilesAbove.map { it.id }.distinct().any { aboveBrickId ->
                    val aboveBrickPos = brickPositions[aboveBrickId]
                    val isHorizontalBrick = aboveBrickPos!!.map { (pos) -> pos.z }.distinct().size == 1
                    if (isHorizontalBrick) {
                        val aboveBrickPosBelow = aboveBrickPos.map { (pos) -> pos.zShift(Direction.DOWN) }.filterNot { it in currentBricks.map { it.key } }
                        aboveBrickPosBelow.any { hasRecorded(it) }
                    } else {
                        false
                    }
                }
            } else true

           /* bricks.none { (pos) ->
                hasRecorded(pos.zShift(Direction.UP))
            }*/
        }

        return count
    }

    // TODO: Move to libs
    private fun Point3D.zShift(direction: Direction): Point3D = when(direction) {
        Direction.UP -> Point3D(this.x, this.y, this.z + 1)
        Direction.DOWN -> Point3D(this.x, this.y, this.z - 1)
        else -> throw IllegalArgumentException("Cannot shift a 3D point in the z-axis in the $direction direction.。")
    }
}