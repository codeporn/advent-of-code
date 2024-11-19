package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Nineteen(test: Boolean) : Task(test) {

    companion object {
        val workflows: MutableMap<String, Workflow> = mutableMapOf()
        var parts: MutableList<Part> = mutableListOf()
    }

    override fun run() {
        this.prepareInput()
        this.checkParts()


        println("\nSum of accepted part ratings is ${parts.filter { it.accepted }.sumOf { it.rating() }}")

    }

    private fun checkParts() {
        parts.forEach {
            workflows["in"]?.let { it1 -> checkPart(it, it1) }
        }
    }

    private fun checkPart(part: Part, workflow: Workflow) {
        var cont = ""
        val propValue = part.valueMap[workflow?.prop?.value]
        if((propValue?.compareTo(workflow.threshold)!! > 0 && workflow.op == '>') ||
            (propValue?.compareTo(workflow.threshold)!! < 0 && workflow.op == '<')) {
            cont = workflow.pos
        }
        else {
           cont = workflow.neg
        }
        if(cont.equals("A")) {
            part.accepted = true
        }
        else if(cont.equals("R")) {
            part.accepted = false
        }
        else {
            workflows[cont]?.let { checkPart(part, it) }
        }
    }

    private fun prepareInput() {
        this.input.subList(0, this.input.indexOf("")).forEach {
            this.addWorkflow(it.substring(0, it.indexOf("{")), it.substring(it.indexOf("{") + 1, it.indexOf("}")))
        }
        parts = this.input.subList(this.input.indexOf("") + 1, this.input.size).map {
            Part(
                "x=(\\d+),".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0,
                "m=(\\d+),".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0,
                "a=(\\d+),".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0,
                "s=(\\d+)}".toRegex().find(it)?.groups?.get(1)?.value?.toInt() ?: 0)
        }.toMutableList()
        parts.forEach { println(it) }
    }

    private fun addWorkflow(id: String, content: String) {
        val threshold = content.substring(2, content.indexOf(":")).toInt()
        val execution = content.substring(content.indexOf(":") + 1)
        var neg = execution.substring(execution.indexOf(",") + 1)

        if(neg.length > 3) {
            addWorkflow(id + "*", neg)
            neg = id + "*"
        }

        workflows[id] = Workflow(id, Prop.from(content[0].toString()), content[1], threshold, execution.substring(0, execution.indexOf(",")), neg)
    }

    data class Workflow(val id: String, val prop: Prop, val op: Char, val threshold: Int, val pos: String, val neg: String) {
        override fun toString(): String {
            return "Workflow(id='$id', prop=$prop, op=$op, threshold=$threshold, pos=$pos, neg=$neg)"
        }
    }

    data class Part(val valueMap: Map<String, Int>) {
        constructor(x: Int, m: Int, a: Int, s: Int):
            this(mapOf(
                "x" to x,
                "m" to m,
                "a" to a,
                "s" to s
            ))

        var accepted = false
        fun rating(): Long = valueMap.values.sumOf { it.toLong() }
        override fun toString(): String {
            return "Part(valueMap=$valueMap, accepted=$accepted)"
        }
    }

    enum class Prop(val value: String) {
        LOOKS("x"),
        MUSIC("m"),
        AERO("a"),
        SHINE("s");
        companion object {
            infix fun from(value: String): Prop = Prop.values().firstOrNull { it.value == value } ?: LOOKS
        }
    }
}