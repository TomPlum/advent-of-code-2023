package io.github.tomplum.aoc.hash

import io.github.tomplum.libs.extensions.product

class HashAlgorithm(private val input: String) {
    fun run(): Int = input.split(",").sumOf { step -> step.hash() }

    fun calculateFocusingPower(): Int {
        val boxes = mutableMapOf<Int, MutableList<Pair<String, Int>>>()
        (0..255).forEach { number -> boxes[number] = mutableListOf() }

        input.split(",").forEach { step ->
            val (label, focalLength) = if ('-' in step) step.split("-") else step.split("=")
            val box = label.hash()

            val lenses = boxes[box]!!

            if ('-' in step) {
                val lens = lenses.find { (lensLabel) -> lensLabel == label }
                if (lens != null) {
                    val lensIndex = lenses.indexOf(lens)
                    lenses.removeAt(lensIndex)
                    boxes[box] = lenses
                }
            } else {
                val newLens = Pair(label, focalLength.toInt())
                val existingLens = lenses.find { (lensLabel) -> lensLabel == label }

                if (existingLens != null) {
                    boxes[box] = lenses.map {
                        if (it.first == label) {
                            Pair(it.first, focalLength.toInt())
                        } else it
                    }.toMutableList()
                } else {
                    boxes[box] = (lenses + newLens).toMutableList()
                }
            }
        }

        return boxes.entries.sumOf { (box, lenses) ->
            lenses.sumOf { lens ->
                listOf(1 + box,  lenses.indexOf(lens) + 1, lens.second).product()
            }
        }
    }

    private fun String.hash() = this.fold(0) { value, character -> ((value + character.code) * 17) % 256 }
}