package io.github.tomplum.aoc.module

sealed class Module {
    companion object {
        fun fromConfigString(config: String): Module {
            return when (config.first()) {
                '%'  -> FlipFlop(name = config.drop(1), powerStatus = false)
                '&'  -> Conjunction(name = config.drop(1), history = mutableMapOf())
                else -> Broadcaster(name = config)
            }
        }
    }

    abstract val name: String

    open fun send(pulse: PulseType): PulseType = pulse
    open fun receive(source: String, pulse: PulseType) {}


    data class Broadcaster(override val name: String) : Module()

    data class FlipFlop(override val name: String, var powerStatus: Boolean) : Module() {
        override fun send(pulse: PulseType): PulseType {
            return if (pulse == PulseType.HIGH) {
                PulseType.KEEPALIVE
            } else {
                if (powerStatus) PulseType.HIGH else PulseType.LOW
            }
        }

        override fun receive(source: String, pulse: PulseType) {
            if (pulse == PulseType.LOW) {
                powerStatus = !powerStatus
            }
        }
    }

    data class Conjunction(override val name: String, val history: MutableMap<String, PulseType>) : Module() {
        override fun send(pulse: PulseType): PulseType {
            return if (history.values.all { type -> type == PulseType.HIGH }) {
                PulseType.LOW
            } else {
                PulseType.HIGH
            }
        }

        override fun receive(source: String, pulse: PulseType) {
            history[source] = pulse
        }

    }

    data class NoOpKeepAlive(override val name: String) : Module() {
        override fun send(pulse: PulseType) = PulseType.KEEPALIVE
    }
}