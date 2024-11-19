package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Six(test: Boolean) : Task(test) {

    companion object {

        var topResults: List<Pair<Long, Long>> = listOf()
        var topResult: List<Pair<Long, Long>> = listOf()
    }

    override fun run() {
        prepareInput()
        println("Multiplied winning strategies for multiple top results: ${this.multiplyWinningStrategies(topResults)}")
        println("Multiplied winning strategies for single top result: ${this.multiplyWinningStrategies(topResult)}")
    }

    fun multiplyWinningStrategies(resultList: List<Pair<Long, Long>>): Int =
        resultList.map { pair ->
            (0..pair.first).map {
                ( pair.first - it) * it
            }.filter {
                it > pair.second
            }.count()
        }.reduce { acc, i -> acc * i }

    fun prepareInput() {
        topResults = this.input.map {
            it.split(" ").mapNotNull {
                it.toLongOrNull() } }.let {
                (time, distance) -> time.zip(distance) }

        var pairList = this.input.map {
            it.split(":")[1]
                .replace("\\s+".toRegex(), "")
                .toLong()
            }
        topResult = listOf(Pair(pairList[0], pairList[1]))
    }
}