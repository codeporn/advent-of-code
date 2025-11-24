package io.wende.aoc.fifteen

import io.wende.aoc.common.Task

class One (test: Boolean) : Task(test) {

    override fun run() {

        val finalLevel = input[0].map { char-> if (char==')') -1 else 1}.sum()
        println("Final level is $finalLevel")

        var level = 0
        var i = 0
        while(level >= 0) {
            if (input[0][i++] == ')') level -= 1 else level += 1
        }

        println("First instruction to reach basement is at position $i")
    }
}