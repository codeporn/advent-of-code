package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Seven : Task() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Seven().run()
        }

        var hands: MutableList<HandWithJokers> = mutableListOf()
        val highCardsWithJoker = mapOf(
            'A' to 'Z',
            'K' to 'Y',
            'Q' to 'X',
            'T' to 'V',
            'J' to '1'
        )
    }

    fun run() {
        this.prepareInput(false)
        println("Total winnings are ${hands.mapIndexed { index, hand -> (index + 1) * hand.bid() }.sum()}")

        this.prepareInput(true)
        println("Total winnings with jokers are ${hands.mapIndexed { index, hand -> (index + 1 ) * hand.bid() }.sum()}")
    }

    private fun prepareInput(withJokers: Boolean) {
        hands = this.input.map {
            if(withJokers) HandWithJokers(it) else HandWithJokers(it.replace("J", "W")) }
            .sortedWith <HandWithJokers> { h0, h1 ->
                if (h0.score() > h1.score()) { 1 }
                else if (h0.score() == h1.score()) { h0.comparableHand().compareTo(h1.comparableHand()) }
                else { -1 }
        }.toMutableList()
    }

    data class HandWithJokers(val source: String) {
        private fun hand(): String = source.split(" ")[0]
        private fun jokers(): Int = hand().count { it == 'J' }
        fun bid(): Int = source.split(" ")[1].toInt()
        fun comparableHand(): String = hand().map { highCardsWithJoker[it] ?: it }.joinToString("")

        fun score(): Int {
            val multiples = hand().replace("J", "").groupingBy { it }.eachCount().map { it.value }.sortedDescending()
            var score = 0
            if (multiples.getOrElse(0) { 0 } + jokers() >= 5) { // five of a kind
                score = 6
            } else if (multiples.getOrElse(0) { 0 } + jokers() >= 4) { // four of a kind
                score = 5
            } else if (multiples.getOrElse(0) { 0 } + multiples.getOrElse(1) { 0 } + jokers() == 5) { // full house
                score = 4
            } else if (multiples.getOrElse(0) { 0 } + jokers() == 3) { // three of a kind
                score = 3
            } else if (multiples.getOrElse(0) { 0 } + multiples.getOrElse(1) { 0 } + jokers() == 4) { // two pairs
                score = 2
            } else if (multiples[0] + jokers() == 2) { // one pair
                score = 1
            } else { // high card
                score = 0
            }
            return score
        }
    }
}