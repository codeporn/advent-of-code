package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task

class Ten(test: Boolean) : Task(test) {

    companion object {
        val lightsReg = """\[([\.\#]+)\]""".toRegex()
        val buttonsReg = """\]\s\((.*)\)\s\{""".toRegex()
        val joltsReg = """\{([\d,]+)\}""".toRegex()
    }

    override fun run() {

        var buttonPresses = this.input.sumOf { line ->
            bfs(lightsReg.find(line)!!.groups[1]!!.value.length,
                lightTarget(lightsReg.find(line)!!.groups[1]!!.value),
                buttonMasks(buttonsReg.find(line)!!.groups[1]!!.value.split(") (").map { it.split(",").map { s -> s.toInt() } }))
        }
        println("There are at least $buttonPresses button presses needed.")

        buttonPresses = this.input.sumOf { line ->
            ilp(joltsReg.find(line)!!.groups[1]!!.value.split(",").map { it.toInt() }.toIntArray(),
                buttonsReg.find(line)!!.groups[1]!!.value.split(") (").map { it.split(",").map { s -> s.toInt() } })
        }
        println("There are at least $buttonPresses button presses needed.")
    }

    fun lightTarget(pattern: String): Int {
        var target = 0

        pattern.forEachIndexed { i, c ->
            if (c == '#') {
                target = target or (1 shl i)
            }
        }
        return target
    }

    fun buttonMasks(schema: List<List<Int>>): List<Int> {
        val buttonMasks = schema.map { indices ->
            var mask = 0
            for (i in indices) {
                mask = mask or (1 shl i)
            }
            mask
        }
        return buttonMasks
    }

    fun ilp(targets: IntArray, buttonSchemas: List<List<Int>>): Int {
        val nCounters = targets.size
        val nButtons = buttonSchemas.size

        if (targets.all { it == 0 }) return 0

        val buttons: Array<IntArray> = Array(nButtons) { j ->
            val v = IntArray(nCounters)
            for (idx in buttonSchemas[j]) {
                v[idx] = 1
            }
            v
        }

        val maxDeltaForSuffix = Array(nButtons + 1) { IntArray(nCounters) { 0 } }
        for (k in nButtons - 1 downTo 0) {
            val cur = maxDeltaForSuffix[k]
            val next = maxDeltaForSuffix[k + 1]
            val btn = buttons[k]
            for (i in 0 until nCounters) {
                cur[i] = maxOf(next[i], btn[i])
            }
        }

        var best = Int.MAX_VALUE
        val remaining = targets.clone()

        fun dfs(buttonIndex: Int, pressesSoFar: Int) {
            if (pressesSoFar >= best) return

            if (buttonIndex == nButtons) {
                if (remaining.all { it == 0 }) {
                    best = pressesSoFar
                }
                return
            }

            val maxDelta = maxDeltaForSuffix[buttonIndex]
            var lowerBound = 0
            for (i in 0 until nCounters) {
                val rem = remaining[i]
                if (rem > 0) {
                    val md = maxDelta[i]
                    if (md == 0) {
                        return
                    }
                    val need = (rem + md - 1) / md  // ceil(rem / md)
                    if (need > lowerBound) lowerBound = need
                }
            }
            if (pressesSoFar + lowerBound >= best) return

            val btn = buttons[buttonIndex]

            var affectsAny = false
            for (i in 0 until nCounters) {
                if (btn[i] != 0) {
                    affectsAny = true
                    break
                }
            }
            if (!affectsAny) {
                dfs(buttonIndex + 1, pressesSoFar)
                return
            }

            var maxTimes = Int.MAX_VALUE
            for (i in 0 until nCounters) {
                if (btn[i] != 0) {
                    val rem = remaining[i]
                    if (rem < maxTimes) maxTimes = rem
                }
            }
            if (maxTimes == Int.MAX_VALUE) {
                dfs(buttonIndex + 1, pressesSoFar)
                return
            }

            fun applyTimes(t: Int) {
                for (i in 0 until nCounters) {
                    remaining[i] -= btn[i] * t
                }
            }

            fun rollbackTimes(t: Int) {
                for (i in 0 until nCounters) {
                    remaining[i] += btn[i] * t
                }
            }

            for (t in maxTimes downTo 0) {
                applyTimes(t)
                var valid = true
                for (i in 0 until nCounters) {
                    if (remaining[i] < 0) {
                        valid = false
                        break
                    }
                }
                if (valid) {
                    dfs(buttonIndex + 1, pressesSoFar + t)
                }
                rollbackTimes(t)
            }
        }

        dfs(0, 0)
        return if (best == Int.MAX_VALUE) -1 else best
    }

    fun bfs(lights: Int, target: Int, buttonMasks: List<Int>): Int {

        val q: ArrayDeque<Int> = ArrayDeque()
        val dist = IntArray(1 shl lights) { -1 }
        q.add(0)
        dist[0] = 0

        while (q.isNotEmpty()) {
            val s = q.removeFirst()
            if (s == target) break

            for (mask in buttonMasks) {
                val next = s xor mask
                if (dist[next] == -1) {
                    dist[next] = dist[s] + 1
                    q.add(next)
                }
            }
        }

        val pressesForThisMachine = dist[target]
        return pressesForThisMachine
    }
}