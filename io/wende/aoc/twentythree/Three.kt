package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Three : Task() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Three().run()
        }
    }

    val inputLines = this.prepareInput()

    fun run() {
        var sum = 0

        inputLines.forEachIndexed { i, line ->
            println("Processing line $i")
            var symbolIndexes: List<Pair<String, Int>> = "[^\\d\\.]".toRegex()
                .findAll(line).map { Pair(it.value, it.range.first) }.toList()
            if(symbolIndexes.size > 0) {
                println("   Symbol Indexes $symbolIndexes")
            }
            symbolIndexes.forEach {
                println("   Processing symbol ${it.first}")
                var start = if(it.second - 3 < 0) 0 else it.second - 3
                var end = if(it.second + 4 > line.length) line.length else it.second + 4
                var candidate = line.substring(start, end)

                sum += this.extractSameLineNumbers(it.first, candidate)
                sum += this.extractAdjacentLineNumbers(inputLines.get(i - 1).substring(start, end))
                sum += this.extractAdjacentLineNumbers(inputLines.get(i + 1).substring(start, end))
            }
        }

        println("Sum is $sum")
    }

    private fun extractAdjacentLineNumbers(candidate: String): Int {
        var (left, right, center) = arrayOf(0, 0, 0)

        if(candidate.substring(3, 4) == ".") {
            left = "\\d+\$".toRegex().find(candidate.substring(0, 3))?.value?.toInt() ?: 0
            right = "^\\d+".toRegex().find(candidate.substring(4))?.value?.toInt() ?: 0
        }
        else {
            center = "\\d+".toRegex().find(
                candidate.substring(1, 6)
                    .replace("\\.\\d$".toRegex(), "").replace("^\\d\\.".toRegex(), "")
            )?.value?.toInt() ?: 0
        }
        if(left > 0) {
            println("       Found adjacent left $left")
        }
        if(right > 0) {
            println("       Found adjacent right $right")
        }
        if(center > 0) {
            println("       Found adjacent center $center")
        }

        return left + right + center;
    }

    private fun extractSameLineNumbers(symbol: String, candidate: String): Int {
        val left = "(\\d+)\\$symbol".toRegex().find(candidate)?.groups?.get(1)?.value?.toInt() ?: 0
        if(left > 0) {
            println("       Found same left $left")
        }

        val right = "\\$symbol(\\d+)".toRegex().find(candidate)?.groups?.get(1)?.value?.toInt() ?: 0
        if(right > 0) {
            println("       Found same right $right")
        }

        return left + right
    }

    private fun prepareInput(): MutableList<String> {
        val verticalBounds = ".".repeat(this.input.get(0).length + 2)
        val extendedInput: MutableList<String> = mutableListOf(verticalBounds, verticalBounds)
        extendedInput.addAll(1, this.input.map { ".$it." })
        extendedInput.forEach { println(it) }
        return extendedInput
    }
}