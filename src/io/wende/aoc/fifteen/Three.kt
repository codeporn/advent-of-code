package io.wende.aoc.fifteen

import Point
import io.wende.aoc.common.Task

class Three (test: Boolean) : Task(test) {

    companion object {
        var visitedHouses : MutableMap<Point, Int> = mutableMapOf()
        var splitHouses : MutableMap<Point, Int> = mutableMapOf()
    }

    override fun run() {
        val currentPos = Point(0,0)
        visitedHouses[currentPos.snap()] = 1

        this.input[0].map { char -> Direction.from(char.toString()) }
            .forEach { dir ->
                val workingPos = currentPos.walk(dir).snap()
                visitedHouses[workingPos] = visitedHouses.getOrElse(workingPos) { 0 }  + 1
            }

        println("Houses visited: ${visitedHouses.count()}")
        println("Houses visited more than once: ${visitedHouses.count{ entry -> entry.value > 1 }}")

        val currentSanta = Point(0,0)
        val currentRobo = Point(0,0)

        splitHouses[currentSanta.snap()] = 2

        this.input[0].map { char -> Direction.from(char.toString()) }
            .forEachIndexed { i, dir ->
                val workingPos = if(i % 2 == 0) {
                    currentSanta.walk(dir).snap()
                } else {
                    currentRobo.walk(dir).snap()
                }
                splitHouses[workingPos] = splitHouses.getOrElse(workingPos) { 0 } + 1
            }
        println("Houses visited: ${splitHouses.count()}")
        println("Houses visited more than once: ${splitHouses.count{ entry -> entry.value > 1 }}")

    }
}