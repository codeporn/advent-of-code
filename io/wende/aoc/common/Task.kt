package io.wende.aoc.common

import kotlin.io.path.Path
import kotlin.io.path.readLines

abstract class Task {
    val input: List<String> =
        Path("data/${this.javaClass.packageName.split(".").last()}/${this.javaClass.simpleName.lowercase()}")
            .readLines()
}