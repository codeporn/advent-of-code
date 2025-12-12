package io.wende.aoc.twentyfive

import Point
import io.wende.aoc.common.Task
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Nine(test: Boolean) : Task(test) {

    companion object {
        lateinit var coordinates: List<Point>
        lateinit var polygon: List<Point>
        lateinit var edges: List<Pair<Point, Point>>
    }
    override fun run() {
        coordinates = this.input.map { line ->
            Point(line.substringBefore(",").toLong(), line.substringAfter(",").toLong())
        }

        var largestArea = 0L
        for (i in 0 until coordinates.size) {
            for (j in i + 1 until coordinates.size) {
                val w = abs(coordinates[i].x - coordinates[j].x) + 1
                val h = abs(coordinates[i].y - coordinates[j].y) + 1
                val area = w * h
                if (largestArea < area) {
                    largestArea = area
                }
            }
        }
        println("Largest possible area is $largestArea")

        largestArea = 0L
        polygon = coordinates + coordinates.first()
        edges = polygon.windowed(2) { it[0] to it[1] }

        for (i in 0 until coordinates.size) {
            for (j in i + 1 until coordinates.size) {
                val p1 = coordinates[i]
                val p2 = coordinates[j]

                if (p1.x == p2.x || p1.y == p2.y) continue

                val x1 = min(p1.x, p2.x)
                val x2 = max(p1.x, p2.x)
                val y1 = min(p1.y, p2.y)
                val y2 = max(p1.y, p2.y)

                val a = Point(x1, y1)
                val b = Point(x2, y1)
                val c = Point(x2, y2)
                val d = Point(x1, y2)

                if (!pointInPolygon(a) ||
                    !pointInPolygon(b) ||
                    !pointInPolygon(c) ||
                    !pointInPolygon(d)
                ) continue

                if (polygonCutsInterior(x1, x2, y1, y2)) continue

                val w = x2 - x1 + 1
                val h = y2 - y1 + 1
                val area = w * h

                if (area > largestArea) {
                    largestArea = area
                }
            }
        }
        println("Largest possible area in polygon is $largestArea")
    }

    fun pointOnSegment(p: Point, a: Point, b: Point): Boolean {
        val cross = (b.x - a.x) * (p.y - a.y) - (b.y - a.y) * (p.x - a.x)
        if (cross != 0L) return false

        val minX = min(a.x, b.x)
        val maxX = max(a.x, b.x)
        val minY = min(a.y, b.y)
        val maxY = max(a.y, b.y)

        return p.x in minX..maxX && p.y in minY..maxY
    }

    fun pointInPolygon(p: Point): Boolean {
        if (edges.any { (a, b) -> pointOnSegment(p, a, b) }) { return true }

        var inside = false
        for (i in 0 until polygon.size - 1) {
            val a = polygon[i]
            val b = polygon[i + 1]

            val yi = a.y
            val yj = b.y
            val xi = a.x
            val xj = b.x

            val intersects = (yi > p.y) != (yj > p.y) && (p.x <= (xj - xi) * (p.y - yi).toDouble() / (yj - yi).toDouble() + xi)

            if (intersects) { inside = !inside }
        }
        return inside
    }

    fun polygonCutsInterior(x1: Long, x2: Long, y1: Long, y2: Long): Boolean {
        for ((a, b) in edges) {
            if (a.x == b.x) {
                val xv = a.x
                if (xv <= x1 || xv >= x2) continue

                val sy1 = min(a.y, b.y)
                val sy2 = max(a.y, b.y)

                val overlapLow = max(sy1, y1 + 1)
                val overlapHigh = min(sy2, y2 - 1)

                if (overlapLow <= overlapHigh) { return true }
            } else if (a.y == b.y) {
                val yh = a.y
                if (yh <= y1 || yh >= y2) continue

                val sx1 = min(a.x, b.x)
                val sx2 = max(a.x, b.x)

                val overlapLow = max(sx1, x1 + 1)
                val overlapHigh = min(sx2, x2 - 1)

                if (overlapLow <= overlapHigh) { return true }
            } else {
                continue
            }
        }
        return false
    }
}
