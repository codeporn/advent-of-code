package io.wende.aoc.twentyfive

import Point
import io.wende.aoc.common.Task
import kotlin.math.abs


class Nine(test: Boolean) : Task(test) {

    companion object {

    }

    override fun run() {
        val coordinates = this.input.map { line -> Point(line.substringBefore(",").toLong(), line.substringAfter(",").toLong()) }

        var largestArea: Long = 0
        for(i in 0..<coordinates.size) {
            for(j in 0..<coordinates.size) {
                val area = (abs(coordinates[i].x - coordinates[j].x) + 1) * (abs(coordinates[i].y - coordinates[j].y) + 1)
                if(largestArea < area)
                    largestArea = area
            }
        }

        println("Largest possible area is ${largestArea}")
    }
}