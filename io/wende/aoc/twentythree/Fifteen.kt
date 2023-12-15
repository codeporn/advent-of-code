package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Fifteen(test: Boolean) : Task(test) {

    companion object {
        var initSequence: List<String> = listOf()
    }

    override fun run() {
        this.prepareInput()

        println("\nSum of hash results is ${initSequence.sumOf {
            it.hash()
        }}")
    }

    private fun prepareInput() {
        initSequence = this.input[0].split(",")
    }

    private fun String.hash(): Int {
        var ret: Int = 0
        if(this.isNotBlank()) {
            this.chars().forEach {
                ret += it
                ret *= 17
                ret %= 256
            }
        } else { return -1 }
        return ret
    }
}