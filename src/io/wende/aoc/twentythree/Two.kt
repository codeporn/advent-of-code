package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Two(test: Boolean) : Task(test) {

    companion object {

        val games: List<Game> = mutableListOf()
    }

    override fun run (){
        this.parseGames()
        println("Sum of IDs of possible games with params [r:12, g:13, b:14] is ${
            this.findPossibleGames(12, 13, 14).sumOf { it.id }
        }")
        println("Summed up power of all games is ${games.sumOf { it.maxRed() * it.maxGreen() * it.maxBlue() }}")
    }

    private fun findPossibleGames(red:Int, green:Int, blue:Int): List<Game> {
        return games.filter {
            (it.maxRed() <= red) && (it.maxGreen() <= green) && (it.maxBlue() <= blue)
        }
    }

    private fun parseGames() {
        this.input.forEach {
            val steps: List<Step> = mutableListOf()
            it.split(":")[1].split(";").forEach {
                steps.addLast(Step(
                    (" (\\d+) r".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0),
                    (" (\\d+) g".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0),
                    (" (\\d+) b".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0)
                ))
            }
            games.addLast("\\d+".toRegex().find(it.split(":")[0])?.value?.toInt()?.let { it1 -> Game(it1, steps) })
        }
    }

    class Game(var id: Int, private var steps:List<Step>) {
        fun maxRed() : Int{
            return steps.maxOfOrNull { it.red } ?: 0
        }

        fun maxGreen() : Int {
            return steps.maxOfOrNull { it.green } ?: 0
        }

        fun maxBlue() : Int {
            return steps.maxOfOrNull { it.blue } ?: 0
        }
    }

    class Step(var red:Int, var green:Int, var blue:Int)
}

