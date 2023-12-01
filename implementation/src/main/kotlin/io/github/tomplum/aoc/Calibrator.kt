package io.github.tomplum.aoc

class Calibrator(val input: List<String>) {
    fun calibrate(): Int {
       return input
           .map { it.filter { char -> char.isDigit() } }
           .map { it.first().toString() + it.last() }
           .sumOf { it.toInt() }
    }

    fun calibrateWithWords(): Int {
        return input
            .map {
                var i = 0
                val digits = mutableListOf<Any>()
                while (i < it.length) {
                    if (it[i].isDigit()) {
                        digits.add(it[i].toString().toInt())
                        i++
                    } else if(words.keys.any { word -> word.first() === it[i] }) {
                        val found = words.keys.find { word ->
                            val canFit = (it.length - i) >= word.length
                            if (it[i] in word && canFit) {
                                val rest = word.drop(1)
                                return@find it.subSequence(i + 1, i + rest.length + 1) == rest
                            }
                            false
                        }

                        if (found != null) {
                            digits.add(found)
                            i += found.length
                        } else {
                            i++
                        }
//                        words.keys.forEach { word ->
//                            if (it[i] in word) {
//                                val rest = word.drop(1)
//                                if (it.subSequence(i + 1, i + rest.length + 1) == rest) {
//                                    digits.add(word)
//                                    i += word.length
//                                    return@forEach
//                                }
//                            }
//                        }
                    } else {
                        i++
                    }
                }
                digits.map { value ->
                    if (value in words.keys) {
                        words[value]?: 0
                    } else {
                        value.toString().toInt()
                    }
                }
            }
            .map { it.first().toString() + it.last() }
            .sumOf { it.toInt() }
    }

    private val words = mapOf(
        Pair("one", 1), Pair("two", 2), Pair("three", 3), Pair("four", 4), Pair("five", 5),
        Pair("six", 6), Pair("seven", 7), Pair("eight", 8), Pair("nine", 9)
    )
}