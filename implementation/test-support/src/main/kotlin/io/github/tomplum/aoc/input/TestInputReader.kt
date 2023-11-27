package io.github.tomplum.aoc.input

import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.input.types.Input
import io.github.tomplum.libs.input.types.IntegerInput
import io.github.tomplum.libs.input.types.LongInput
import io.github.tomplum.libs.input.types.StringInput
import java.io.File

class TestInputReader private constructor(){
    companion object {
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T : Any> read(path: String): Input<T> {
            val lines = File(InputReader::class.java.getResource("/input/$path").path).readLines()

            return when (val cls = T::class.java) {
                String::class.java -> StringInput(lines) as Input<T>
                Int::class.javaObjectType -> IntegerInput(lines) as Input<T>
                Long::class.javaObjectType -> LongInput(lines) as Input<T>
                else -> throw UnsupportedOperationException("Input Reader does not support type: ${cls.simpleName}")
            }
        }
    }
}
