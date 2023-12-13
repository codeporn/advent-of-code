package io.wende.aoc.twentythree

import io.wende.aoc.common.Task
import kotlin.math.abs

class Eleven(test: Boolean) : Task(test) {

    companion object {
        var listOfEmptyRows: MutableList<Int> = mutableListOf()
        var listOfEmptyColumns: MutableList<Int> = mutableListOf()
        var galaxies: MutableList<Pair<Int, Int>> = mutableListOf()

    }
    override fun run() {
        this.prepareInput()
        this.findGalaxies()

        println("Sum of shortest length between galaxies is ${calc(1)}")
        println("Sum of shortest length between galaxies is ${calc(999999)}")
    }

    private fun calc(exp: Int): Long{
        var sum: Long = 0
        galaxies.forEach {src ->
            galaxies.forEach { dst ->
                sum += abs(src.first - dst.first) + (exp * findCountBetween(listOfEmptyColumns, src.first, dst.first)) +
                        abs(src.second - dst.second) + (exp * findCountBetween(listOfEmptyRows, src.second, dst.second))
            }
        }
        return sum / 2
    }

    private fun findCountBetween(list: List<Int>, a: Int, b: Int): Int {
        var low: Int = 0
        var high: Int = 0
        if(a < b) {
            low = a
            high = b
        }
        else {
            low = b
            high = a
        }
        return list.filter { it > low && it < high }.size
    }

    private fun findGalaxies() {
        this.input.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                if(c == '#') {
                    galaxies.addLast(Pair(x, y))
                }
            }
        }
    }

    private fun prepareInput() {
        listOfEmptyColumns = this.input[0]
            .mapIndexed { index, c -> if(c == '.') index else -1 }
            .filter { it > -1 }
            .toMutableList()

        this.input.forEach {
            it.forEachIndexed { index, c ->
                if(c != '.') {
                    listOfEmptyColumns.remove(index)
                }
            }
        }

        this.input.forEachIndexed { index, line ->
            if(!line.contains("#")) {
                listOfEmptyRows.addLast(index)
            }
        }
    }
}