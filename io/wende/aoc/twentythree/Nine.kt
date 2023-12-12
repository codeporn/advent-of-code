package io.wende.aoc.twentythree

import io.wende.aoc.common.Task


class Nine(test: Boolean) : Task(test) {

    override fun run() {
        println("Sum of extrapolated values is ${input.sumOf { this.extrapolate(mutableListOf(it.split(" ").map { it.toInt() }.toMutableList())) }}")
        println("Sum of backwards extrapolated values is ${input.sumOf { this.extrapolate(mutableListOf(it.split(" ").map { it.toInt() }.toMutableList()), true) }}")
    }

    private fun extrapolate(vals: MutableList<MutableList<Int>>, left: Boolean = false): Int {
        if(vals.last().all { it == 0 }) {
            (vals.size - 2 downTo 0).forEach {
                if(left) {
                    vals[it].add(0, vals[it].first() - vals[it + 1].first())
                } else {
                    vals[it].add(vals[it].last() + vals[it + 1].last())
                }
            }
            return if(left) vals[0].first() else vals[0].last()
        }
        else {
            vals.addLast((0 until vals.last().size - 1).map {
                vals.last()[it + 1] - vals.last()[it]
            }.toMutableList())
            return this.extrapolate(vals, left)
        }
    }
}