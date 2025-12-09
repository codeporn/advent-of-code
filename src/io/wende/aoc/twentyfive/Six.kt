package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task

class Six(test: Boolean) : Task(test) {
    companion object {
        lateinit var problems: MutableList<MutableList<String>>
        val spacex = """\s+""".toRegex()
    }

    override fun run() {
        problems = MutableList(this.input[0].split(spacex).size) { mutableListOf() }
        this.input.map { line -> line.trim().split(spacex) }.forEach { line ->
                line.forEachIndexed { i, value ->
                    problems[i].add(value)
                }
            }

        val res = problems.sumOf { problem ->
            val op = problem.last()
            problem.subList(1, problem.size - 1)
                .fold(problem[0].toLong()) { acc, value ->
                    if (op == "*") acc * value.toLong() else acc + value.toLong()
                }
        }
        println("Sum of results is $res")

        val rows = input.size
        val cols = input.maxOf { it.length }

        val grid = Array(rows) { y ->
            val line = input[y]
            CharArray(cols) { x -> if (x < line.length) line[x] else ' ' }
        }

        var total = 0L
        var x = 0

        while (x < cols) {
            var columnEmpty = true
            for (y in 0 until rows) {
                if (grid[y][x] != ' ') {
                    columnEmpty = false
                    break
                }
            }

            if (columnEmpty) {
                x++
                continue
            }

            val startCol = x
            var cur = x
            while (cur < cols) {
                var colEmptyInner = true
                for (y in 0 until rows) {
                    if (grid[y][cur] != ' ') {
                        colEmptyInner = false
                        break
                    }
                }
                if (colEmptyInner) break
                cur++
            }
            val endCol = cur - 1
            x = cur

            val bottomRow = rows - 1
            var op: Char? = null
            for (cx in startCol..endCol) {
                val ch = grid[bottomRow][cx]
                if (ch == '*' || ch == '+') {
                    op = ch
                    break
                }
            }

            if (op == null) continue


            val numbers = mutableListOf<Long>()
            for (cx in startCol..endCol) {
                val sb = StringBuilder()
                for (y in 0 until bottomRow) {
                    val ch = grid[y][cx]
                    if (ch.isDigit()) {
                        sb.append(ch)
                    }
                }
                if (sb.isNotEmpty()) {
                    numbers.add(sb.toString().toLong())
                }
            }

            if (numbers.isNotEmpty()) {
                val value = numbers
                    .drop(1)
                    .fold(numbers.first()) { acc, v ->
                        if (op == '*') acc * v else acc + v
                    }
                total += value
            }
        }

        println("Cephalopod math result is $total")
    }
}
