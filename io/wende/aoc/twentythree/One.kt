package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class One : Task() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            One().run()
        }
    }

    fun run() {
        val sum = this.input.map { line ->
            "\\d".toRegex().findAll(line)
                .map { it.value }
                .joinToString("")
        }.filter {
            it.isNotBlank()
        }.sumOf {
            "${it.first()}${it.last()}".toInt()
        }

        println("Sum of all coordinates is $sum")

        val replacements = mapOf(
            "one" to "o1e",
            "two" to "t2o",
            "three" to "t3e",
            "four" to "f4r",
            "five" to "f5e",
            "six" to "s6x",
            "seven" to "s7n",
            "eight" to "e8t",
            "nine" to "n9e"
        )

        val sumWithLiterals = this.input.map { line ->
            "\\d".toRegex().findAll(line.replaceAll(replacements))
                .map { it.value }
                .joinToString("")
        }.filter {
            it.isNotBlank()
        }.sumOf {
            "${it.first()}${it.last()}".toInt()
        }

        println("Sum of all coordinates incl. literals is $sumWithLiterals")
    }

    private fun String.replaceAll(replacements: Map<String, String>): String {
        var res = this
        replacements.entries.forEach { entry ->
            res = res.replace(entry.key, entry.value)
        }

        return res
    }
}