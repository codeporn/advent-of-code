package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Sample(test: Boolean) : Task(test) {
    override fun run (){
        val calories = mutableListOf(0)
        this.input?.forEach {
            if(it.isBlank()) {
                calories.add(0)
            }
            else {
                calories[calories.size - 1] = calories.last().plus(it.toInt())
            }
        }
        println("Elf calories ranges from ${calories.sorted().first()} to ${calories.sorted().last()}")
    }
}