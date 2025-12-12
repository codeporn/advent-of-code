package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task

class Four(test: Boolean) : Task(test) {

    companion object {
        var accessibleRolls = 0
        var map = mutableListOf<MutableList<Int>>()

        var removedRolls = 0
        var removeCycle = 0
    }

    override fun run() {
        this.prepare()

        for(y in 1..map.size - 2) {
            for(x in 1.. map[y].size - 2) {
                if(map[y][x] > 0 && accessible(x,y)) {
                    accessibleRolls++
                }
            }
        }
        println("$accessibleRolls are accessible")

        do {
            removeCycle = 0
            for(y in 1..map.size - 2) {
                for(x in 1.. map[y].size - 2) {
                    if(map[y][x] > 0 && accessible(x,y)) {
                        map[y][x] = 0
                        removeCycle++
                    }
                }
            }
            removedRolls += removeCycle
        } while (removeCycle > 0)
        println("$removedRolls have been removed")
    }

    fun prepare() {
        map.add(MutableList(this.input[0].length + 2) { 0 })
        this.input.forEach { line ->
            val mapLine = mutableListOf(0, 0)
            mapLine.addAll(1, line.map { if(it == '@') 1 else 0 }.toMutableList())
            map.add(mapLine)
        }
        map.add(MutableList(this.input[0].length + 2) { 0 })
    }

    fun accessible(x: Int, y: Int): Boolean {
        var rollsFound = -1
        for(i in (x - 1)..(x + 1)) {
            for (j in (y -1)..(y + 1)) {
                rollsFound += map[j][i]
            }
        }
        return rollsFound < 4
    }
}