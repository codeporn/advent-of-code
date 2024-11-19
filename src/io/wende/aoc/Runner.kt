package io.wende.aoc

import findTaskInstance
import findTodaysTaskInstance
import isNumeric

object Runner {

    @JvmStatic
    fun main(args: Array<String>) {
        val date = this.findDateArgs(args)
        val test = this.findTestArg(args)
        if(date != null) {
            println("Running task for ${date.first}/${date.second} in ${if(test) "test" else "live"} mode.")
            findTaskInstance(date.first, date.second, test)?.run()
        }else {
            println("Running today's task in ${if(test) "test" else "live"} mode.")
            findTodaysTaskInstance(test)?.run()
        }
    }

    private fun findTestArg(args: Array<String>): Boolean = (args.size == 1 && args[0].equals("true")) ||
                                                                (args.size == 3 && args[2].equals("true"))

    private fun findDateArgs(args: Array<String>): Pair<Int, Int>? = if(args.size >= 2 && args[0].isNumeric() && args[1].isNumeric()) Pair(args[0].toInt(), args[1].toInt()) else null
}