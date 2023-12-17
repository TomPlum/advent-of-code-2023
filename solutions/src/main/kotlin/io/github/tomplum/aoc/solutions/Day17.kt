package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.city.CityMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day17: Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(17)).value
    private val cityMap = CityMap(data)

    override fun part1(): Int {
        return cityMap.directCrucibleFromLavaPoolToMachinePartsFactory()
    }

    override fun part2(): Int {
        // 806 too low
        return cityMap.directUltraCrucibleFromLavaPoolToMachinePartsFactory()
    }
}