package io.github.tomplum.aoc

class Calibrator(val input: List<String>) {
    fun calibrate(): Int {
       return input
           .map { it.filter { char -> char.isDigit() } }
           .map { it.first().toString() + it.last() }
           .sumOf { it.toInt() }
    }
}