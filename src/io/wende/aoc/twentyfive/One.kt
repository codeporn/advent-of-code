package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task
import kotlin.math.abs

class One(test: Boolean) : Task(test) {

    companion object {
        var passCount = 0
        var hexPassCount = 0.0
    }

    override fun run() {
        this.input.map { it ->
            it.substring(1).toInt() * if(it.startsWith("R")) 1 else -1
        }.fold(50) { current, steps ->
            val calcDestination = current + steps
            val destination = (calcDestination % 100 + 100) % 100

            if (destination == 0) passCount++

            var rounds = abs(calcDestination / 100)
            if (steps < 0 && current != 0 && calcDestination <= 0) rounds++
            hexPassCount += rounds

            destination
        }

        println("Password: $passCount")
        println("Hex Password: ${hexPassCount.toInt()}")
    }
}