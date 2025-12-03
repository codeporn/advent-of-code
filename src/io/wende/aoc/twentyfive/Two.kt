package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task

class Two(test: Boolean) : Task(test) {

    companion object {
        val repeated = """^(\d+)\1$""".toRegex()
        val repeatedMulti = """^(\d+)\1+$""".toRegex()
        var invalidSum = 0L
        var invalidMultiSum = 0L
    }

    override fun run() {
        this.input[0].split(",").map { range ->
            Pair(range.substringBefore("-").toLong(), range.substring(range.indexOf("-") + 1).toLong())
        }.forEach { pair ->
            for(id in pair.first..pair.second) {
                if(repeated.matches(id.toString())) {
                    invalidSum += id
                }
                if(repeatedMulti.matches(id.toString())) {
                    invalidMultiSum += id
                }
            }
        }
        println("Sum of repeated ids is $invalidSum")
        println("Sum of repeated multiple ids is $invalidMultiSum")
    }
}