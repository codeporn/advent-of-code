package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Three : Task() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Three().run()
        }

        val numbers: MutableList<NumberLocation> = mutableListOf()
        val symbols: MutableList<SymbolLocation> = mutableListOf()
    }

    fun run() {
        this.prepareInput()
        println("Sum is ${this.calculatePartNumbers()}")
        println("Sum of gear ratio is ${this.calculateGearRatios()}")
    }

    private fun calculatePartNumbers(): Int {
        val iterator = numbers.iterator()
        while (iterator.hasNext()) {
            val number = iterator.next()
            if (symbols.none { this.isInRange(number, it) }) {
                iterator.remove()
            }
        }
        return numbers.sumOf { it.value.toInt() }
    }

    private fun calculateGearRatios(): Int {
        return symbols.filter { it.value == "*" }
            .sumOf { gear ->
                val rangedNumbers = numbers.filter { this.isInRange(it, gear) }
                if(rangedNumbers.size == 2) {
                    rangedNumbers.first().value.toInt() * rangedNumbers.last().value.toInt()
                } else 0
            }
    }

    private fun isInRange(number: NumberLocation, symbol: SymbolLocation): Boolean {
       return (
               (symbol.y == number.y - 1 || symbol.y == number.y + 1) &&
                       (number.x.second >= symbol.x - 1 && number.x.first <= symbol.x + 1)) || // line above/below
                (symbol.y == number.y && (number.x.second == symbol.x - 1 || number.x.first == symbol.x + 1))  // same line
    }

    private fun prepareInput() {
        this.input.forEachIndexed { i, s ->
            "\\d+".toRegex().findAll(s).forEach {
                numbers.add(NumberLocation(Pair(it.range.first, it.range.last), i, it.value))
            }

            "[^\\d\\.]".toRegex().findAll(s).forEach {
                symbols.add(SymbolLocation(it.range.first, i, it.value))
            }
        }
    }

    data class NumberLocation(var x:Pair<Int, Int>, var y: Int, var value: String) {
        override fun toString() = "NumberLocation(xLocation=${x.first}:${x.second} y=$y value=$value)"
    }

    data class SymbolLocation(var x: Int, var y: Int, var value: String) {
        override fun toString() = "SymbolLocation(x=$x y=$y value=$value)"
    }
}

