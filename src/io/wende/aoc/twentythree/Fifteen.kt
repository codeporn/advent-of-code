package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Fifteen(test: Boolean) : Task(test) {

    companion object {
        var initSequence: List<String> = listOf()
        var boxes: MutableList<MutableList<Lens>> = MutableList(256) { mutableListOf<Lens>() }
    }

    override fun run() {
        this.prepareInput()
        println("\nSum of hash results is ${initSequence.sumOf {
            it.hash()
        }}")

        this.rearrangeLenses()
        println("\nSum of hash results is ${boxes.withIndex().sumOf { indexedBox ->
            indexedBox.value.withIndex().sumOf {indexedLens ->
                (indexedBox.index + 1) * (indexedLens.index + 1) * indexedLens.value.fl
            }
        }}")
    }

    private fun rearrangeLenses() = initSequence.forEach {
        if (it.endsWith('-')) {
            val lensId = it.dropLast(1)
            boxes[lensId.hash()].removeIf { it.id == lensId }
        } else {
            val lens = Lens(it.split('=')[0], it.split('=')[1].toInt())
            val index = boxes[lens.id.hash()].indexOf(lens)
            if (index == -1) {
                boxes[lens.id.hash()].add(lens)
            } else {
                boxes[lens.id.hash()][index] = lens
            }
        }
    }

    private fun prepareInput() {
        initSequence = this.input[0].split(",")
    }

    private fun String.hash(): Int {
        var ret: Int = 0
        if(this.isNotBlank()) {
            this.chars().forEach {
                ret += it
                ret *= 17
                ret %= 256
            }
        } else { return -1 }
        return ret
    }

    data class Lens(val id: String, var fl: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Lens) return false
            if (id != other.id) return false
            return true
        }

        override fun hashCode(): Int = id.hashCode()
    }
}