package io.github.tomplum.aoc.module

import io.github.tomplum.libs.extensions.product
import io.github.tomplum.libs.logging.AdventLogger

enum class ModuleType {
    FLIP_FLOP, CONJUNCTION, BROADCAST
}

enum class PulseType {
    HIGH, LOW, KEEPALIVE
}

abstract class Module(open val name: String, val type: ModuleType, open val destinationModules: List<String>) {
    abstract fun execute(pulse: PulseType): Pair<PulseType, List<String>>
}

data class BroadcastModule(override val destinationModules: List<String>): Module("broadcaster", ModuleType.BROADCAST, destinationModules) {
    override fun execute(pulse: PulseType):  Pair<PulseType, List<String>> {
        return pulse to destinationModules
    }
}

data class FlipFlopModule(
    override val name: String,
    override val destinationModules: List<String>,
    var powerStatus: Boolean = false
): Module(name, ModuleType.FLIP_FLOP, destinationModules) {
    override fun execute(pulse: PulseType): Pair<PulseType, List<String>> {
        if (pulse == PulseType.LOW) {
            if (powerStatus) {
                powerStatus = false
                return PulseType.LOW to destinationModules
            } else {
                powerStatus = true
                return PulseType.HIGH to destinationModules
            }
        }

        return PulseType.KEEPALIVE to emptyList()
    }
}

data class ConjunctionModule(
    override val name: String,
    override val destinationModules: List<String>,
    val history: MutableMap<String, PulseType>
): Module(name, ModuleType.CONJUNCTION, destinationModules) {
    override fun execute(pulse: PulseType): Pair<PulseType, List<String>> {
        return if (history.values.all { type -> type == PulseType.HIGH }) {
            PulseType.LOW to destinationModules
        } else {
            PulseType.HIGH to destinationModules
        }
    }
}

class CableNetwork(private val config: List<String>) {
    fun getPulseCount(): Long {
        val modules = config.associate { moduleConfig ->
            val (left, right) = moduleConfig.split("->")
            val destinationModules = right.trim().split(", ")
            val name = left.drop(1).trim()

            if ("broadcaster" in moduleConfig) {
                "broadcaster" to BroadcastModule(destinationModules)
            } else if ('%' in moduleConfig) {
                name to FlipFlopModule(name, destinationModules)
            } else if ('&' in moduleConfig) {
                name to ConjunctionModule(name, destinationModules, mutableMapOf())
            } else {
                throw IllegalArgumentException("Unknown Module Configuration $moduleConfig")
            }
        }.toMutableMap()

        val activeModules = ArrayDeque<Triple<String, PulseType, Module>>()

        val pulsesSent = mutableMapOf<PulseType, Long>()

        fun pushTheAptlyButton() {
             AdventLogger.error("button -low-> broadcaster")
            activeModules.add(Triple("aptly",  PulseType.LOW, modules["broadcaster"]!!))
            pulsesSent.compute(PulseType.LOW) { _, count ->
                if (count == null) 1 else count + 1
            }
        }

        repeat(1000) {
            pushTheAptlyButton()

            while(activeModules.isNotEmpty()) {
                val (sourceModule, incomingPulse, currentDestination) = activeModules.removeFirst()

                if (currentDestination.type == ModuleType.CONJUNCTION) {
                    modules.computeIfPresent(currentDestination.name) { _, module ->
                        (module as ConjunctionModule).apply {
                            history[sourceModule] = incomingPulse
                        }
                    }
                }

                val (outgoingPulseType, destinationModules) = currentDestination.execute(incomingPulse)

                pulsesSent.compute(outgoingPulseType) { _, count ->
                    if (count == null) destinationModules.size.toLong() else destinationModules.size + count
                }

                destinationModules.forEach { destinationModuleName ->
                     AdventLogger.error("${currentDestination.name} -${outgoingPulseType.name.lowercase()}-> $destinationModuleName")
                    val newDestination = modules[destinationModuleName]
                    if (newDestination != null) {
                        activeModules.addLast(Triple(currentDestination.name, outgoingPulseType, modules[destinationModuleName]!!))
                    }
                }
            }
        }

        return pulsesSent.values.filter { value -> value > 0 }.toList().product()
    }
}