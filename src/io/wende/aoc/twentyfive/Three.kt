package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task
import permutations
import kotlin.text.substring

class Three(test: Boolean) : Task(test) {

    companion object {
        var joltageSum = 0
        var bigJoltageSum = 0L
    }

    override fun run() {
        this.input.forEach { bank ->
            val cent = bank.dropLast(1).map { it.toString().toInt() }.maxOf { it }
            val single = bank.substring(bank.indexOf(cent.toString()) + 1).map { it.toString().toInt() }.maxOf { it }
            joltageSum += "$cent$single".toInt()

            val first = bank.dropLast(11).map { it.toString().toInt() }.maxOf { it }
            bigJoltageSum += this.condense(first.toString(), 10, bank.substring(bank.indexOf(first.toString()) +1 )).toLong()
        }

        println("Simple joltage sum is $joltageSum")
        println("Extended joltage sum is $bigJoltageSum")
    }

    fun condense(starter: String, minChars: Int, search: String): String {
        val next = search.dropLast(minChars).map { it.toString().toInt() }.maxOf { it }
        return if(minChars > 0)
            this.condense("$starter$next", minChars - 1, search.substring(search.indexOf(next.toString()) +1 ))
        else
            "$starter$next"
    }
}