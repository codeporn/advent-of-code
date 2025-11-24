package io.wende.aoc.fifteen

import io.wende.aoc.common.Task

class Two (test: Boolean) : Task(test) {

    override fun run() {
        val area = this.input.sumOf { line -> Present(line).getArea() }
        println("The amount of necessary wrapping paper is ${area} feet.")
        val length = this.input.sumOf { line -> Present(line).getLength() }
        println("The amount of necessary ribbon is ${length} feet.")
    }

    data class Present(val l: Int, val w: Int, val h: Int) {
        constructor(str: String): this(str.split("x")[0].toInt(), str.split("x")[1].toInt(), str.split("x")[2].toInt())

        fun getBow(): Int = l*w*h
        fun getRibbon(): Int = mutableListOf(l, w, h).sorted().subList(0,2).sumOf { dim -> 2 * dim }
        fun getLength(): Int = getRibbon() + getBow()

        val surfaces = listOf(2*l*w, 2*w*h, 2*h*l)
        fun getSurface(): Int = this.surfaces.sum()
        fun getSlack(): Int = this.surfaces.min() / 2
        fun getArea(): Int = this.getSurface() + this.getSlack()
    }
}