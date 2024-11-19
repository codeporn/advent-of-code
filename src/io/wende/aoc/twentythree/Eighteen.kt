package io.wende.aoc.twentythree

import Direction
import Point

import io.wende.aoc.common.Task
import outerGraphArea
import kotlin.math.absoluteValue

class Eighteen(test: Boolean) : Task(test) {

    companion object {
        var alphaInstructions: MutableList<Pair<Direction, Int>> = mutableListOf()
        var hexInstructions: MutableList<Pair<Direction, Int>> = mutableListOf()
        var trench: MutableList<Point> = mutableListOf()
    }

    override fun run() {
        this.prepareInput()
        this.traceTrench(Point(0, 0), alphaInstructions)
        println("\nDug out area is ${outerGraphArea(trench.dropLast(1))}")

        trench.clear()
        // this.traceTrench(Point(0, 0), hexInstructions)  // Heap space
        // println("\nDug out area is ${outerGraphArea(trench.dropLast(1))}")
        println("\nDug out area is ${traceAndShoelacePick(hexInstructions)}")
    }

    private fun traceTrench(start: Point, instructions: MutableList<Pair<Direction, Int>>) {
        trench.addFirst(start)
        var status: Point = Point(start.x, start.y)

        for(instruction in instructions) {
            for(j in 0 until instruction.second) {
                status.walk(instruction.first)
                trench.addLast(status.snap())
            }
        }
    }

    private fun traceAndShoelacePick(instructions: MutableList<Pair<Direction, Int>>): Long  {
        var steps = 0L
        var shoelace = 0L
        var status = Point(0, 0)
        instructions.forEachIndexed { index, instruction ->
            for(j in 0 until instruction.second) {
                var curr = status.snap()
                status.walk(instruction.first)
                steps++
                shoelace += (curr.x * status.y) - (curr.y * status.x)
            }
        }
        return ((shoelace.absoluteValue / 2) - steps/2 + 1) + steps
    }

    private fun prepareInput() {
        alphaInstructions = this.input.map {
            Pair(Direction.from(it[0].toString()), it.drop(2).substring(0, it.drop(2).indexOf(" ")).toInt())
        }.toMutableList()

        hexInstructions = this.input.map {
            var hex = it.substring(it.indexOf("(#") + 2, it.indexOf(")"))
            var dir = when(hex.last()) {
                '0' -> Direction.EAST
                '1' -> Direction.SOUTH
                '2' -> Direction.WEST
                '3' -> Direction.NORTH
                else -> Direction.NORTH
            }
           Pair(dir, Integer.parseInt(hex.dropLast(1), 16))
        }.toMutableList()
    }
}