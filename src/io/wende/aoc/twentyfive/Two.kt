package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task

class Two(test: Boolean) : Task(test) {

    companion object {
        val repeated = """^(\d+)\1$""".toRegex()
        val repeatedMulti = """^(\d+)\1+$""".toRegex()
    }

    override fun run() {

        println("Sum of repeated ids is ${this.input[0].split(",")
            .map { range -> Pair(range.substringBefore("-").toLong(), range.substring(range.indexOf("-") + 1).toLong()) }
            .sumOf { pair -> (pair.first..pair.second).sumOf { id -> if(repeated.matches(id.toString())) { id } else { 0 } } }}")

        println("Sum of repeated multiple ids is ${ this.input[0].split(",")
                .map { range -> Pair(range.substringBefore("-").toLong(), range.substring(range.indexOf("-") + 1).toLong()) }
                .sumOf { pair -> (pair.first..pair.second).sumOf { id -> if (repeatedMulti.matches(id.toString())) { id } else { 0 } } } }")
    }
}