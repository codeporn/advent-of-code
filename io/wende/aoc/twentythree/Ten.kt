package io.wende.aoc.twentythree

import io.wende.aoc.common.Task
import kotlin.math.absoluteValue

class Ten(test: Boolean) : Task(test) {

    companion object {
        var map: List<List<Tile>> = listOf(listOf())
        var pipeLength: Long = -1
        var pipeTiles: MutableList<Tile> = mutableListOf()
    }

    override fun run() {
        this.prepareInput()
        val start = this.findStart()
        pipeTiles.addFirst(start) // needed for shoelace
        pipeLength = this.countPipeLength(1, start, start.neighbours()[0])
        println("Farthest distance is ${pipeLength / 2}")
        println("Number of enclosed tiles is ${area() - (pipeLength / 2) + 1}")
    }

    // Shoelace formula
    private fun area(): Int = (0 until pipeTiles.size - 1).sumOf {
            (pipeTiles[it].x * pipeTiles[(it + 1) ].y) - (pipeTiles[it].y * pipeTiles[(it + 1)].x)
        }.absoluteValue / 2

    private tailrec fun countPipeLength(counter: Long, prev: Tile, status: Tile): Long {
        pipeTiles.addFirst(status) // needed for shoelace
        if(status.isStart()) {
            return counter
        } else {
            return countPipeLength(counter + 1, status, status.neighbours().filter { !it.equals(prev) }.first())
        }
    }

    private fun findStart(): Tile {
        map.forEachIndexed { y, tiles ->
            tiles.forEachIndexed { x, tile ->
                if(tile.isStart()) {
                    return tile
                }
            }
        }
        return Tile('*', -1, -1)
    }

    private fun prepareInput() {
        this.input.addFirst(".".repeat(this.input[0].length + 2))
        this.input.addLast(".".repeat(this.input[0].length + 2))
        map = this.input.mapIndexed { y, line ->
            ".${line}.".mapIndexed { x, c -> Tile(c, x, y) }
        }
    }

    data class Tile(val c: Char, val x: Int, val y: Int) {
        private val pipeTypes = listOf('|', '-', 'L', 'J', '7', 'F', 'S')
        private fun isPipe(): Boolean = pipeTypes.contains(c)
        fun isStart(): Boolean = c == 'S'
        fun neighbours(): List<Tile> {
            var n: MutableList<Tile> = mutableListOf()

            if (c == '|' || c == 'L' || c == 'J') {
                n.addLast(map[y - 1][x])
            } else if (c == 'F' || c == '-') {
                n.addLast(map[y][x + 1])
            } else if (c == '7') {
                n.addLast(map[y + 1][x])
            }

            if (c == '|' || c == 'F') {
                n.addLast(map[y + 1][x])
            } else if (c == 'J' || c == '-' || c == '7') {
                n.addLast(map[y][x - 1])
            } else if (c == 'L') {
                n.addLast(map[y][x + 1])
            }

            if (c == 'S') {
                if (map[y - 1][x].isPipe()) {
                    n.addLast(map[y - 1][x])
                }
                if (map[y][x + 1].isPipe()) {
                    n.addLast(map[y][x + 1])
                }
                if (map[y + 1][x].isPipe()) {
                    n.addLast(map[y + 1][x])
                }
                if (map[y][x - 1].isPipe()) {
                    n.addLast(map[y][x - 1])
                }
            }

            return n
        }

        override fun equals(other: Any?) = (other is Tile)
                && x == other.x
                && y == other.y
                && c == other.c
    }
}