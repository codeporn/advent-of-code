package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Four : Task() {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Four().run()
        }
    }

    private val cards: MutableList<Card> = mutableListOf()

    fun run (){
        this.parseCards()

        println("Sum of all points is ${cards.map { it.getPoints() }.sum()}")

        val count = cards.map { 1 }.toMutableList()
        for (index in cards.indices) {
            val score = cards[index].getMatches().size
            for (i in index+ 1 ..< index+ 1 + score) {
                count[i] += count[index]
            }
        }

        println("Final number of scratchcards: ${count.sum()}")
    }

    fun parseCards() {
        this.input?.forEach {
            val winningNumbers: List<Int> = it.split(":").get(1)
                .split("|").get(0).trim()
                .split("\\s+".toRegex())
                .map { it.toInt() }
            val guesses: List<Int> = it.split(":").get(1)
                .split("|").get(1).trim()
                .split("\\s+".toRegex())
                .map { it.toInt() }
            this.cards.addLast("\\d+".toRegex().find(it.split(":").get(0))?.value?.toInt()?.let {
                it1 -> Card(it1, winningNumbers, guesses) })
        }
    }
}

class Card(var id: Int, var winningNumbers: List<Int>, var guesses: List<Int>) {
    fun getMatches(): Set<Int> {
        return winningNumbers.intersect(guesses)
    }

    fun calcualtePoints(n: Int): Int {
        var ret = 1
        if(n == 0) {
            ret = 0
        }
        else if(n > 1){
            for(i in 1..n-1) {
                ret *= 2
            }
        }
        return ret
    }

    fun getPoints(): Int {
        return this.calcualtePoints(this.getMatches().size)
    }

    override fun toString() = "Card(id=$id, matches=${this.getMatches().size}, points=${this.getPoints()})"
}
