package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Thirteen(test: Boolean) : Task(test) {

    companion object {
        var patterns: List<Pattern> = listOf()
    }

    override fun run() {
        this.prepareInput()

        println("\nSummarized notes add up to ${patterns.sumOf {
            verticalMiddle(it.rotatedPattern) + (100 * verticalMiddle(it.originalPattern))
        }}")
        println("\nSummarized notes with anomalies add up to ${patterns.sumOf {
            verticalMiddleWithAnomaly(2, it.rotatedPattern) + (100 * verticalMiddleWithAnomaly(2, it.originalPattern))
        }}")
    }

    private fun verticalMiddle(pattern: List<List<Char>>): Int = verticalMiddleWithAnomaly(0, pattern)

    private fun verticalMiddleWithAnomaly(tolerance: Int, pattern: List<List<Char>>): Int {
        for (i in 0 until pattern.size - 1) {
            var anomalies = 0
            for (j in 0 until pattern.size) {
                val k = i + (i - j) + 1
                if (k >= 0 && k < pattern.size) {
                    anomalies += pattern[j].mapIndexed { index, c -> if(pattern[k][index] == c) null else 1 }.filterNotNull().size
                }
            }
            if (anomalies == tolerance) {
                return i + 1
            }
        }
        return 0
    }

    private fun prepareInput() {
        patterns = this.input.joinToString("\n").split("\n\n").map { Pattern(it) }
    }

    data class Pattern(val input: String) {
        val originalPattern: List<List<Char>> = preparePattern(input)
        val rotatedPattern: List<List<Char>> = rotatePattern(originalPattern)

        private fun preparePattern(input: String): List<List<Char>> = input.split("\n").map { it.toCharArray().asList() }
        private fun rotatePattern(input: List<List<Char>>): List<List<Char>> =
            List(input[0].size) { column -> List(input.size) { row -> input[row][column] }.reversed() }

        fun out() {
            originalPattern.forEach { println(it) }
            println()
            rotatedPattern.forEach { println(it) }
        }
    }
}