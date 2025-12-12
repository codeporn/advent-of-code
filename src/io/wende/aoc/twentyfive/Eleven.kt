package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task

class Eleven(test: Boolean) : Task(test) {

    companion object {
        lateinit var devices: Map<String, List<String>>
    }

    override fun run() {
        devices = input.associate { it.substringBefore(":") to it.substringAfter(" ").split(" ") }

        println("There are ${depthFirst("you", "out")} different paths from 'you' to 'out'.")
        println("There are ${(depthFirst("svr", "dac") * depthFirst("dac", "fft") * depthFirst("fft", "out")) +
                (depthFirst("svr", "fft") * depthFirst("fft", "dac") * depthFirst("dac", "out"))} different paths visiting 'dac' and 'fft'.")
    }

    fun depthFirst(source: String, target: String, memory: MutableMap<String, Long> = mutableMapOf()): Long =
        if (source == target) 1L
        else memory.getOrPut(source) {
            devices[source]?.sumOf { next ->
                depthFirst(next, target, memory)
            } ?: 0
        }
}
