package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class One : Task() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            One().run()
        }

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
    }

    fun run() {
        val sum = this.input.map { line ->
            line.extractDigits()
        }.filter {
            it.isNotBlank()
        }.sumOf {
            it.firstAndLastAsInt()
        }

        println("Sum of all coordinates is $sum")

        val sumWithLiterals = this.input.map { line ->
            line.replaceAll(replacements).extractDigits()
        }.filter {
            it.isNotBlank()
        }.sumOf {
            it.firstAndLastAsInt()
        }

        println("Sum of all coordinates incl. literals is $sumWithLiterals")
    }

    // We could of course check "this" for not being blank here, as well
    private fun String.firstAndLastAsInt(): Int =
        "${this.first()}${this.last()}".toInt()

    private fun String.extractDigits() = "\\d".toRegex()
        .findAll(this)
        .map { it.value }
        .joinToString("")

    private fun String.replaceAll(replacements: Map<String, String>): String {
        var res = this
        replacements.entries.forEach { entry ->
            res = res.replace(entry.key, entry.value)
        }

        return res
    }
}