package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Twelve(test: Boolean) : Task(test) {

    companion object {
        //var inPairs: List<Triple<String, Regex, Int>> = listOf()
        var inPairs: List<Pair<String, String>> = listOf()
    }

    override fun run() {
        this.prepareInput()

        println(
            "Result ${
                inPairs.sumOf {
                    countArrangements(mutableMapOf(), it.first, it.second.split(",").map { it.toInt() }, 0, 0, 0)
                }
            }"
        )

        println(
            "Unfolded result ${
                inPairs.sumOf {
                    countArrangements(mutableMapOf(),
                        it.first.plus("?").repeat(5).dropLast(1),
                        it.second.plus(",").repeat(5).dropLast(1).split(",").map { it.toInt() }, 0, 0, 0
                    )
                }
            }"
        )
    }

    private fun prepareInput() {
        inPairs = this.input.map {
            //Triple(it.split(" ")[0], it.split(" ")[1].split(",").map { "#{$it}" }.joinToString("\\.+").toRegex(), 0)
            Pair(it.split(" ")[0], it.split(" ")[1])
        }
    }

    fun countArrangements(
        blockMap: MutableMap<Triple<Int, Int, Int>, Long>,
        map: String, amounts: List<Int>,
        i: Int, j: Int, cur: Int ): Long {

        val key = Triple(i, j, cur)
        if (blockMap.containsKey(key)) {
            return blockMap[key]!!
        }
        if (i == map.length) {
            return if ((j == amounts.size && cur == 0) || (j == amounts.size - 1 && amounts[j] == cur)) 1 else 0
        }

        var total: Long = 0
        if ((map[i] == '.' || map[i] == '?') && cur == 0) {
            total += countArrangements(blockMap, map, amounts, i + 1, j, 0)
        }
        else if ((map[i] == '.' || map[i] == '?') && (cur > 0) && (j < amounts.size) && (amounts[j] == cur)) {
            total += countArrangements(blockMap, map, amounts, i + 1, j + 1, 0)
        }

        if (map[i] == '#' || map[i] == '?') {
            total += countArrangements(blockMap, map, amounts, i + 1, j, cur + 1)
        }

        blockMap[key] = total
        return total
    }
}