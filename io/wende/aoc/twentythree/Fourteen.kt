package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Fourteen(test: Boolean) : Task(test) {

    companion object {
        var dishes: List<Dish> = mutableListOf()
    }

    override fun run() {
        this.prepareInput()
        dishes[0].tilt(Direction.NORTH)
        println("\nTotal load is ${dishes[0].load()}")
    }

    private fun prepareInput() {
        dishes = this.input.joinToString("\n").split("\n\n").map { Dish(it) }
    }

    data class Dish(val input: String) {
        val originalGrid: MutableList<MutableList<Char>> = prepareGrid(input)
        var currentOrientation: MutableList<MutableList<Char>> = originalGrid

        private fun prepareGrid(input: String): MutableList<MutableList<Char>> = input.split("\n").map { it.toCharArray().asList().toMutableList() }.toMutableList()

        fun get(): MutableList<MutableList<Char>> = currentOrientation
        fun currentOut() = currentOrientation.forEach { println(it) }
        fun originalOut() = originalGrid.forEach { println(it) }

        fun rotateClockwise(degrees: Degrees) {
            currentOrientation = when(degrees) {
                Degrees.NINETY ->
                    MutableList(currentOrientation[0].size) { column -> MutableList(currentOrientation.size) { row -> currentOrientation[row][column] }.reversed().toMutableList() }

                Degrees.ONE_EIGHTY ->
                    currentOrientation.map { it.reversed().toMutableList() }.reversed().toMutableList()

                Degrees.TWO_SEVENTY ->
                    MutableList(currentOrientation[0].size) { column -> MutableList(currentOrientation.size) { row -> currentOrientation[row][column] } }.reversed().toMutableList()
            }
        }

        fun tilt(direction: Direction) {
            when(direction){
                Direction.NORTH -> rotateClockwise(Degrees.TWO_SEVENTY)
                Direction.EAST -> rotateClockwise(Degrees.ONE_EIGHTY)
                Direction.SOUTH -> rotateClockwise(Degrees.NINETY)
                Direction.WEST -> null
            }

            currentOrientation.forEach {
                var movement = true
                while (movement) {
                    movement = false
                    for (i in 0..it.size - 2) {
                        if (it[i] == '.' && it[i + 1] == 'O') {
                            it[i] = 'O'
                            it[i + 1] = '.'
                            movement = true
                        }
                    }
                }
            }

            when(direction){
                Direction.NORTH -> rotateClockwise(Degrees.NINETY)
                Direction.EAST -> rotateClockwise(Degrees.ONE_EIGHTY)
                Direction.SOUTH -> rotateClockwise(Degrees.TWO_SEVENTY)
                Direction.WEST -> null
            }
        }

        fun load(): Int =
            currentOrientation.reversed().mapIndexed { index, chars ->
                chars.count { it == 'O' } * (index + 1)
            }.sum()
    }

    enum class Degrees {
        NINETY, ONE_EIGHTY, TWO_SEVENTY
    }

    enum class Direction {
        NORTH, EAST, SOUTH, WEST
    }
}