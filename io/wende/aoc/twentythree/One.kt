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
        var sum = this.input.map {
            "\\d".toRegex().findAll(it)
                .map { it.value }
                .joinToString("")
        }.map {
            "${it.first()}${it.last()}".toInt()
        }.sum()

        println("Sum of all coordinates is $sum")

        val literals = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val numbers = listOf("o1e", "t2o", "t3e", "f4r", "f5e", "s6x", "s7n", "e8t", "n9e")

        sum = this.input.map {
            "\\d".toRegex().findAll(
                it.replaceAll(literals, numbers)
            ).map { it.value }
                .joinToString("")
        }.map {
            "${it.first()}${it.last()}".toInt()
        }.sum()

        println("Sum of all coordinates incl. literals is $sum")
    }

    inline fun String.replaceAll(oldValues: List<String>, replacements: List<String>): String {
        var res = this
        oldValues.forEachIndexed { index, s ->
            res = res.replace(s, replacements[index])
        }
        return res
    }
}