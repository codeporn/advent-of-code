package io.wende.aoc.twentythree

import Direction
import io.wende.aoc.common.Task
import java.util.PriorityQueue

class Twentyone(test: Boolean) : Task(test) {

    companion object {
        var grid:MutableList<MutableList<Char>> = mutableListOf(mutableListOf())
    }

    override fun run() {
        this.prepareInput()

        println("\nTotal load after 1000000000 spin cycles is ${1}")
    }


    private fun prepareInput() {
        grid = this.input.map {
            it.toCharArray().toMutableList()
        }.toMutableList()
    }

}