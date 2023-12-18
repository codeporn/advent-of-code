package io.wende.aoc.twentythree

import Direction
import Point
import io.wende.aoc.common.Task

class Sixteen(test: Boolean) : Task(test) {

    companion object {
        var grid: MutableList<MutableList<Char>> = mutableListOf(mutableListOf())
        var energizedTiles: MutableList<Pair<Point, Direction>> = mutableListOf()
    }

    override fun run() {
        this.prepareInput()
        this.beam(Direction.EAST, Point(0, 0))
        println("\nResult 1 ${energizedTiles.map{ it.first }.distinct().count()}")

        var max: Int = 0
        for(i in 0 until grid.size) {
            for(j in 0 until grid[0].size) {
                if(!(i * j == 0 || i == grid.size - 1 || j == grid[0].size - 1)) {continue}
                for(dir in Direction.values()) {
                    energizedTiles.clear()
                    val dir = if (i == 0) { Direction.EAST }
                    else if(i == grid.size - 1) { Direction.WEST}
                    else if(j == 0) { Direction.SOUTH}
                    else { Direction.NORTH }
                    beam(dir, Point(i.toLong(), j.toLong()))
                    max = maxOf(max, energizedTiles.map{ it.first }.distinct().count())
                }
            }
        }
        println("\nResult 2 ${max}")
    }

    private tailrec fun beam(direction: Direction, curr: Point) {
        if(curr.x < 0 || curr.y < 0 || curr.x >= grid[0].size || curr.y >= grid.size || energizedTiles.contains(Pair(curr, direction))) {
            return
        }
        energizedTiles.add(Pair(curr, direction))

        if(grid[curr.y.toInt()][curr.x.toInt()] == '.' ||
            ((direction == Direction.NORTH || direction == Direction.SOUTH) && grid[curr.y.toInt()][curr.x.toInt()] == '|') ||
            ((direction == Direction.EAST || direction == Direction.WEST) && grid[curr.y.toInt()][curr.x.toInt()] == '-')) {
            when(direction){
                Direction.NORTH -> beam(direction, Point(curr.x, curr.y - 1))
                Direction.EAST -> beam(direction, Point(curr.x + 1, curr.y))
                Direction.SOUTH -> beam(direction, Point(curr.x, curr.y + 1))
                Direction.WEST -> beam(direction, Point(curr.x - 1, curr.y))
            }

        }
        else if(grid[curr.y.toInt()][curr.x.toInt()] == '/') {
            when(direction){
                Direction.NORTH -> beam(Direction.EAST, Point(curr.x + 1, curr.y))
                Direction.EAST -> beam(Direction.NORTH, Point(curr.x, curr.y - 1))
                Direction.SOUTH -> beam(Direction.WEST, Point(curr.x - 1, curr.y))
                Direction.WEST -> beam(Direction.SOUTH, Point(curr.x, curr.y + 1))
            }
        }
        else if(grid[curr.y.toInt()][curr.x.toInt()] == '\\') {
            when(direction){
                Direction.NORTH -> beam(Direction.WEST, Point(curr.x - 1, curr.y))
                Direction.EAST -> beam(Direction.SOUTH, Point(curr.x, curr.y + 1))
                Direction.SOUTH -> beam(Direction.EAST, Point(curr.x + 1, curr.y))
                Direction.WEST -> beam(Direction.NORTH, Point(curr.x, curr.y - 1))
            }
        }
        else if ((direction == Direction.EAST || direction == Direction.WEST) && grid[curr.y.toInt()][curr.x.toInt()] == '|') {
            beam(Direction.SOUTH, Point(curr.x, curr.y + 1))
            beam(Direction.NORTH, Point(curr.x, curr.y - 1))
        }
        else if((direction == Direction.NORTH || direction == Direction.SOUTH) && grid[curr.y.toInt()][curr.x.toInt()] == '-') {
            beam(Direction.WEST, Point(curr.x - 1, curr.y))
            beam(Direction.EAST, Point(curr.x + 1, curr.y))
        }
    }

    private fun prepareInput() {
        grid = this.input.map {
            it.toCharArray().toMutableList()
        }.toMutableList()
    }
}