package io.github.tomplum.aoc.machine

class MachinePartsSorter(data: List<String>) {
    private val sortingData = data.split { line -> line.isBlank()}.let { (workflows, partRatings) ->
        val workflowInstructions = workflows.map { workflowData ->
            val (name, rules) = workflowData.removeSuffix("}").split("{")
            name to rules.split(",")
        }.toMap()

        val parts = partRatings.map { ratingData ->
            ratingData.removeSurrounding("{", "}").split(",").map { rating ->
                val (category, value) = rating.split("=")
                category to value.toInt()
            }.toMap()
        }

        parts to workflowInstructions
    }

    fun sort(): Int {
        val result = sortingData.first.map { it to isPartAccepted(it, sortingData.second["in"]!!) }
        return sortingData.first.filter { partRatings ->
            isPartAccepted(partRatings, sortingData.second["in"]!!)
        }.sumOf { it.values.sum() }
    }

    private fun isPartAccepted(part: Map<String, Int>, workflow: List<String>): Boolean {
        var accepted: Boolean? = null

        for(instruction in workflow) {
            if (instruction.contains(">")) {
                val (category, right) = instruction.split(">")
                val (ratingValue, targetWorkflow) = right.split(":")


                if (part[category]!! > ratingValue.toInt()) {
                    if (targetWorkflow == "R") {
                        accepted = false
                        break
                    }

                    if (targetWorkflow == "A") {
                        accepted = true
                        break
                    }

                    accepted = isPartAccepted(part, sortingData.second[targetWorkflow]!!)
                    break
                }
            } else if (instruction.contains("<")) {
                val (category, right) = instruction.split("<")
                val (ratingValue, targetWorkflow) = right.split(":")

                if (part[category]!! < ratingValue.toInt()) {
                    accepted = isPartAccepted(part, sortingData.second[targetWorkflow]!!)
                    break
                }
            } else if (instruction == "A") {
                accepted = true
                break
            } else if (instruction == "R") {
                accepted = false
                break
            } else {
                accepted = isPartAccepted(part, sortingData.second[instruction]!!)
                break
                //throw IllegalArgumentException("Invalid workflow instruction [$instruction]")
            }
        }

        return accepted ?: throw IllegalArgumentException("Failed to determine part $part is accepted via workflow $workflow")
    }

    // TODO: Import from libs once released
    private fun <T> List<T>.split(predicate: (element: T) -> Boolean): List<List<T>> {
        var i = 0
        val data = mutableMapOf<Int, List<T>>()
        var current = mutableListOf<T>()

        this.forEachIndexed { index, element ->
            if (index == this.size - 1) {
                current.add(element)
                data[i] = current
            } else if (predicate(element)) {
                data[i] = current
                i++
                current = mutableListOf()
            } else {
                current.add(element)
            }
        }

        return data.values.toList()
    }
}