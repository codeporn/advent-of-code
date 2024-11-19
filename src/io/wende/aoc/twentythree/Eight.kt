package io.wende.aoc.twentythree

import io.wende.aoc.common.Task
import lcm

class Eight(test: Boolean) : Task(test) {

    companion object {

        var instr: List<Int> = mutableListOf()
        var steps: Map<String, List<String>> = mutableMapOf()
    }

    override fun run() {
        this.prepareInput()
        println("Steps from AAA to reach ZZZ ${this.navigate("AAA", "ZZZ")}")
        println("Parallel steps from A ending to Z ending nodes ${this.navigateParallel("A", "Z")}")
    }

    private fun navigateParallel(start: String, dest: String): Long =
        steps.filter { it.key.endsWith(start) }.keys.map {
            navigate(it, "Z")
        }.reduce(::lcm)

    private fun navigate(start: String, dest: String): Long {
        var count = 0L
        var pos: String = start
        do {
            val i = instr[(if(count < instr.size) count else count % instr.size).toInt()]
            pos = steps.get(pos)?.get(i) ?: "..."
            count++
        } while(!pos.endsWith(dest))
        return count
    }

    private fun prepareInput() {
        instr = this.input[0].map { if(it == 'L') 0 else 1 }
        steps = this.input.subList(2, this.input.size).associateBy {
            it.split(" = ")[0]
        }.mapValues {
            it.value.split(" = ")[1].replace("[\\(\\)]".toRegex(), "").split(", ")
        }
    }
}