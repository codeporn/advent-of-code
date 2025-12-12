package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task


class Twelve(test: Boolean) : Task(test) {

    companion object {
    }
    override fun run() {
        var possibleRegion = 0
        this.input.drop(30).forEach { line ->
            val regions: List<String> = line.split(": ")
            val (length, width) = regions[0].split("x").map { it.toInt() }
            val sum = regions[1].trim().split(" ").sumOf { it.toInt() }

            if (sum <= length / 3 * width / 3) {
                possibleRegion++
            }
        }
        println("There are ${possibleRegion} regions")
    }
}