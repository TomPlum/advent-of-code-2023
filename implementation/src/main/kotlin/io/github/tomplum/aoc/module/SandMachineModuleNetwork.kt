package io.github.tomplum.aoc.module

import io.github.tomplum.libs.extensions.lcm
import io.github.tomplum.libs.extensions.product

class SandMachineModuleNetwork(config: List<String>) {
    private val destinations = config
        .map { configLine -> configLine.split(" -> ") }
        .associate { (name, destinations) -> name.replace("%", "").replace("&", "") to destinations.split(", ") }

    private val noOpModules = destinations
        .values
        .flatten()
        .filter { name -> name !in destinations.keys }
        .associateWith { name -> Module.NoOpKeepAlive(name) }

    private val operationalModules = config
        .map { Module.fromConfigString(it.split(" -> ").first()) }
        .associateBy { module -> module.name }

    private val modules = operationalModules + noOpModules

    private val aptlyButton = modules.getValue("broadcaster") to PulseType.LOW

    init {
        destinations.forEach { (sourceModuleName, destinationModules) ->
            destinationModules.forEach { destination ->
                val module = modules.getValue(destination)
                if (module is Module.Conjunction) {
                    module.history[sourceModuleName] = PulseType.LOW
                }
            }
        }
    }

    /**
     * Finds the product of the sums of the number of LOW and HIGH strength pulses
     * fired by all [modules] after having pressed the [aptlyButton] 1000 times.
     *
     * @return The final product.
     */
    fun getPulseCount(): Long = (1..1000).fold(mutableMapOf<PulseType, Long>()) { pulses, _ ->
        var activeModules = listOf(aptlyButton)

        while (activeModules.isNotEmpty()) {
            activeModules = buildList {
                activeModules.forEach { (module, incomingPulse) ->
                    pulses.compute(incomingPulse) { _, count ->
                        if (count == null) 1 else count  +1
                    }

                    val outgoingPulse = module.send(incomingPulse)

                    if (outgoingPulse == PulseType.KEEPALIVE) {
                        return@forEach
                    }

                    sendPulseToModules(module, outgoingPulse)
                }
            }
        }

        pulses
    }.values.filter { value -> value > 0 }.toList().product()


    /**
     * Finds the minimum number of times the [aptlyButton] needs to be pressed,
     * with all the modules exhausting their message cycle, until the [targetModuleName]
     * receives its first LOW strength pulse from the conjunction module targeting it.
     *
     * @param targetModuleName The name of the module to search for.
     * @return The number of button presses required.
     */
    fun getButtonPressesRequiredToDeliverToModule(targetModuleName: String): Long {
        // Find the name of the conjunction module that needs to fire a LOW pulse at our target
        val targetConjunctionModule = destinations.entries
            .find { (_, destinations) -> targetModuleName in destinations }!!
            .key

        // A conjunction module will only fire a LOW pulse at its destination modules when all
        // other conjunction modules that target it have last sent a HIGH pulse. Here we find all
        // conjunction modules names that send pulses to the targetConjunctionModule above
        val sourceConjunctionModules = destinations.entries
            .filter { (_, destinations) -> targetConjunctionModule in destinations }
            .map { (sourceModuleName) -> sourceModuleName }

        // Keep track of the minimum number of button presses that need to be fired until each
        // of the tracked conjunction modules fire a HIGH pulse at our targetConjunctionModule
        val watching = sourceConjunctionModules.associateWith { 0L }.toMutableMap()

        var timesButtonPressed = 0L

        while (watching.values.any { pulses -> pulses == 0L }) {
            var activeModules = listOf(aptlyButton)

            timesButtonPressed++

            while (activeModules.isNotEmpty()) {
                activeModules = buildList {
                    activeModules.forEach { (module, incomingPulse) ->
                        val outgoingPulse = module.send(incomingPulse)

                        if (outgoingPulse == PulseType.KEEPALIVE) {
                            return@forEach
                        }

                        if (module.name in watching && outgoingPulse == PulseType.HIGH) {
                            watching[module.name] = timesButtonPressed
                        }

                        sendPulseToModules(module, outgoingPulse)
                    }
                }
            }
        }

        return watching.values.toList().lcm()
    }

    private fun MutableList<Pair<Module, PulseType>>.sendPulseToModules(
        module: Module,
        outgoingPulse: PulseType
    ) {
        destinations.getValue(module.name)
            .map { name -> modules.getValue(name) }
            .forEach { destination ->
                destination.receive(module.name, outgoingPulse)
                add(destination to outgoingPulse)
            }
    }
}