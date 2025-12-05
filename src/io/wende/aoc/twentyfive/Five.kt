package io.wende.aoc.twentyfive

import io.wende.aoc.common.Task

class Five(test: Boolean) : Task(test) {

    companion object {
        var mergedIdRanges = mutableListOf<Pair<Long, Long>>()
        var freshIdRanges = mutableListOf<Pair<Long, Long>>()
        var checkIds = mutableSetOf<Long>()
    }

    override fun run() {
        this.prepare()
        println("Found ${checkIds.count { id ->
            freshIdRanges.find { pair -> 
                pair.first <= id && id <= pair.second
            } != null
        }} fresh ids")


        println("There are ${mergedIdRanges.sumOf { pair -> pair.second - pair.first + 1 }} fresh ids")
    }

    fun prepare() {
        val splitIndex = this.input.indexOf("")
        this.input.subList(0, splitIndex).forEach { range ->
            val newPair = Pair(range.substringBefore("-").toLong(), range.substring(range.indexOf("-") + 1).toLong())
            freshIdRanges.add(newPair)
            mergedIdRanges.add(newPair)
            var len = 0
            var merged = 0
            do {
                len = mergedIdRanges.size
                mergedIdRanges = this.mergeIdRanges(mergedIdRanges)
                merged = mergedIdRanges.size
            } while(len != merged)
        }
        checkIds.addAll(this.input.subList(this.input.indexOf("") + 1, this.input.size).map { it.toLong() })
    }

    fun mergeIdRanges(idRanges: MutableList<Pair<Long, Long>>): MutableList<Pair<Long, Long>> {
        val mergedIdRanges = mutableListOf<Pair<Long, Long>>()
        idRanges.forEach { pair ->
            var merged = false
            for(i in 0..<mergedIdRanges.size) {

                if(pair.first <= mergedIdRanges[i].first && pair.second >= mergedIdRanges[i].second) { // new range bigger than existing
                    mergedIdRanges[i] = pair
                    merged = true
                }
                else if(pair.first >= mergedIdRanges[i].first && pair.second <= mergedIdRanges[i].second) { // new range in existing range
                    merged = true
                }
                else if(pair.first < mergedIdRanges[i].first && pair.second >= mergedIdRanges[i].first && pair.second <= mergedIdRanges[i].second) { // left overlap
                    mergedIdRanges[i] = Pair(pair.first, mergedIdRanges[i].second)
                    merged = true
                }
                else if(pair.first >= mergedIdRanges[i].first && pair.first <= mergedIdRanges[i].second && pair.second > mergedIdRanges[i].second) { // right overlap
                    mergedIdRanges[i] = Pair(mergedIdRanges[i].first, pair.second)
                    merged = true
                }
            }
            if (!merged) {
                mergedIdRanges.add(pair);
            }
        }
        return mergedIdRanges
    }
}