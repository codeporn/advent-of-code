package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Two : Task() {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Two().run()
        }
    }

    private val games: List<Game> = mutableListOf()

    fun run (){
        this.parseGames()
        println("Sum of IDs of possible games with params [r:12, g:13, b:14] is ${
            this.findPossibleGames(12,13,14).map { it.id }.sum()
        }")
        println("Sumemd up power of all games is ${games.map { it.maxRed() * it.maxGreen() * it.maxBlue() }.sum()}")
    }

    fun findPossibleGames(red:Int, green:Int, blue:Int): List<Game> {
        return games.filter {
            it.maxRed() <= red && it.maxGreen() <= green && it.maxBlue() <= blue
        }
    }

    fun parseGames() {
        this.input?.forEach {
            val steps: List<Step> = mutableListOf()
            it.split(":").get(1).split(";").forEach {
                steps.addLast(Step(
                    (" (\\d+) r".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0),
                    (" (\\d+) g".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0),
                    (" (\\d+) b".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0)
                ))
            }
            this.games.addLast("\\d+".toRegex().find(it.split(":").get(0))?.value?.toInt()?.let { it1 -> Game(it1, steps) })
        }
    }
}

class Game(var id: Int, var steps:List<Step>) {
    fun maxRed() : Int{
        return steps.map { it.red }.maxOrNull() ?: 0
    }

    fun maxGreen() : Int {
        return steps.map { it.green }.maxOrNull() ?: 0
    }

    fun maxBlue() : Int {
        return steps.map { it.blue }.maxOrNull() ?: 0
    }
}

class Step(var red:Int, var green:Int, var blue:Int) {}