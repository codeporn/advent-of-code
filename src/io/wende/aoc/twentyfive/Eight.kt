package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task
import zipWithAllUnique
import kotlin.math.pow
import kotlin.math.sqrt

class Eight (test: Boolean) : Task(test) {

companion object {
    var lines = mutableListOf<Junction>()
    var circuits = mutableListOf<MutableSet<Junction>>()
}

    override fun run() {
        prepare()
        for ((a, b) in lines.zipWithAllUnique().sortedBy { (a, b) -> calculateDistance(a, b) }
            .take(lines.size.takeIf { it != 20 } ?: 10)) merge(circuits, a, b)

        val top3 = circuits.sortedByDescending { it.size }.take(3)
        val multiple = top3.subList(1, 3).fold(top3[0].size) { res, value -> res * value.size }
        println("Multiple of larges circuit sizes is $multiple")

        prepare()
        val pairs = lines.zipWithAllUnique().sortedBy { (a, b) -> calculateDistance(a, b) }.iterator()
        var result = 0L
        while (circuits.size > 1) {
            val (a, b) = pairs.next()
            if (merge(circuits, a, b)) {
                result = a.x * b.x
            }
        }
        println("Multiple last two x coordinates $result")
    }

    private fun prepare() {
        lines = this.input.map { Junction(it.split(",")[0].toLong(), it.split(",")[1].toLong(), it.split(",")[2].toLong()) }.toMutableList()
        circuits = lines.map { hashSetOf(it) }.toMutableList()
    }

    private fun merge(circuits: MutableList<MutableSet<Junction>>, a: Junction, b: Junction): Boolean {
        if (circuits.any { a in it && b in it }) return false
        val ca = circuits.first { a in it && b !in it }
        val cb = circuits.first { b in it && a !in it }
        cb.addAll(ca)
        circuits.remove(ca)
        return true
    }

    fun calculateDistance(start: Junction, end: Junction) = sqrt((
            (start.x - end.x).toDouble().pow(2) +
                    (start.y - end.y).toDouble().pow(2) +
                    (start.z - end.z).toDouble().pow(2)))

    class Junction(var x:Long, var y:Long, var z:Long) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Junction

            if (x != other.x) return false
            if (y != other.y) return false
            if (z != other.z) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x.hashCode()
            result = 31 * result + y.hashCode()
            result = 31 * result + z.hashCode()
            return result
        }

        override fun toString(): String {
            return "Point(x=$x, y=$y, z=$z)"
        }
    }
}