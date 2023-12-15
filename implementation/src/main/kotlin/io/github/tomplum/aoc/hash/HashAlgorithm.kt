package io.github.tomplum.aoc.hash

class HashAlgorithm(private val input: String) {
    fun run(): Int = input.split(",")
        .sumOf { step ->
            val s= step.fold(0) { value, character ->
                var new = value
                new += character.code
                new *= 17
                new %= 256
                new
            }
            s
        }
}