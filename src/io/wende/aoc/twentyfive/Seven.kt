package io.wende.aoc.twentyfive

import Point
import io.wende.aoc.common.Task

class Seven(test: Boolean) : Task(test) {

    companion object {
        var splitCounter = 0
        var beamIdices = mutableSetOf<Int>()
        val memo = mutableMapOf<Point, Long>()
        var timelines = 0L
    }

    override fun run() {
        beamIdices.add(this.input[0].indexOf("S"))

        this.input.forEach { level ->
            val splitBeams = mutableSetOf<Int>()
            beamIdices.forEach { beamIndex ->
                if (level[beamIndex] == '^') {
                    splitCounter++
                    splitBeams.add(beamIndex - 1)
                    splitBeams.add(beamIndex + 1)
                } else {
                    splitBeams.add(beamIndex)
                }
            }
            beamIdices = splitBeams
        }

        println("The beam was split $splitCounter times.")


        timelines = proceed(1, this.input[0].indexOf("S"))
        println("There are $timelines possible timelines.")
    }

    fun proceed(level: Int, beamIndex: Int): Long = proceed(Point(beamIndex.toLong(), level.toLong()))

    fun proceed(position: Point): Long {
        val level = position.y.toInt()
        val beamIndex = position.x.toInt()

        if (level !in this.input.indices ||
            beamIndex < 0 ||
            beamIndex >= this.input[level].length
        ) {
            return 1L
        }

        val key = position.snap()
        memo[key]?.let { return it }

        val row = this.input[level]
        val result = if (row[beamIndex] == '^') {
            val nextLevel = level + 1
            proceed(Point((beamIndex - 1).toLong(), nextLevel.toLong())) +
                    proceed(Point((beamIndex + 1).toLong(), nextLevel.toLong()))
        } else {
            proceed(Point(beamIndex.toLong(), (level + 1).toLong()))
        }

        memo[key] = result
        return result
    }
}
