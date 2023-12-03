package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Three : Task() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Three().run()
        }
    }

    fun run() {
        this.prepareInput()

        println("")
    }

    private fun prepareInput(): List<List<Any>>? {
        var lines: List<List<Any>> = mutableListOf()
        this.input?.forEach {
            var chars: List<Any> = mutableListOf()
            it.split("").forEach {

            }
        }
        return null
    }
}