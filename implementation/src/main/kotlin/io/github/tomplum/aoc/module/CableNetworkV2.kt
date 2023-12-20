package io.github.tomplum.aoc.module

import io.github.tomplum.libs.extensions.lcm
import io.github.tomplum.libs.extensions.product

class CableNetworkV2(config: List<String>) {
    private val destinations = config
        .map { configLine -> configLine.split(" -> ") }
        .associate { (name, destinations) -> name.replace("%", "").replace("&", "") to destinations.split(", ") }

    private val modules = config
        .map { ModuleV2.fromConfigString(it.split(" -> ").first()) }
        .associateBy { it.name } + destinations
            .values
            .flatten()
            .filter { name -> name !in destinations.keys }
            .associateWith { ModuleV2.NoOpKeepAlive(it) }

    init {
        destinations.forEach { (source, destinations) ->
            destinations.forEach { destination ->
                val module = modules.getValue(destination)
                if (module is ModuleV2.Conjunction) {
                    module.history[source] = PulseType.LOW
                }
            }
        }
    }

    fun getPulseCount(): Long {
        val pulsesSent = mutableMapOf<PulseType, Long>()

        repeat(1000) {
            var activeModules = listOf(modules.getValue("broadcaster") to PulseType.LOW)

            while (activeModules.isNotEmpty()) {
                activeModules = buildList {
                    for ((module, incomingPulse) in activeModules) {
                        pulsesSent.compute(incomingPulse) { _, count ->
                            if (count == null) 1 else count  +1
                        }

                        val outgoingPulse = module.send(incomingPulse)

                        if (outgoingPulse == PulseType.KEEPALIVE) {
                            continue
                        }

                        destinations.getValue(module.name)
                            .map { name -> modules.getValue(name) }
                            .forEach { destination ->
                                destination.receive(module.name, outgoingPulse)
                                add(destination to outgoingPulse)
                            }
                    }
                }
            }
        }

        return pulsesSent.values.filter { value -> value > 0 }.toList().product()
    }

    fun getButtonPressesRequiredToDeliverToModule(targetModuleName: String): Long {
        // 22803499706691 too low
        val watching = mutableMapOf("js" to 0L, "zb" to 0L, "bs" to 0L, "rr" to 0L)
        var pulsesSent = 0L

        while (watching.values.any { pulses -> pulses == 0L }) {
            var activeModules = listOf(modules.getValue("broadcaster") to PulseType.LOW)

            pulsesSent++

            while (activeModules.isNotEmpty()) {
                activeModules = buildList {
                    for ((module, incomingPulse) in activeModules) {
                        val outgoingPulse = module.send(incomingPulse)

                        if (outgoingPulse == PulseType.KEEPALIVE) {
                            continue
                        }

                        if (module.name in watching && outgoingPulse == PulseType.HIGH) {
                            watching[module.name] = pulsesSent
                        }

                        destinations.getValue(module.name)
                            .map { name -> modules.getValue(name) }
                            .forEach { destination ->
                                destination.receive(module.name, outgoingPulse)
                                add(destination to outgoingPulse)
                            }
                    }
                }
            }
        }

        return watching.values.toList().lcm()
    }
}