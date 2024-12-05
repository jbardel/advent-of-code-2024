package day05

import println
import readInput


val ordering = mutableMapOf<Int, List<Int>>()

fun List<Int>.allDifferent(e: Int): Boolean {
    return this.none { s -> e == s }
}

fun middle(n: Int): Int {
    return (n - 1) / 2
}

fun verifyPosition(elements: List<String>, c: Int): Boolean {
    val elem = elements[c].toInt()
    for (i in 0 until c) {
        if (ordering[elem]?.allDifferent(elements[i].toInt()) == false) {
            return false
        }
    }
    return true
}

fun reorder(elements: List<String>): List<Int> {
    return elements.map { it.toInt() }.sortedWith { o1, o2 ->
        if (ordering[o1]?.contains(o2) == true) {
            -1
        } else if (ordering[o2]?.contains(o1) == true) {
            1
        } else {
            0
        }
    }
}


fun main() {
    fun part1(input: List<String>): Int {

        return input.map { line ->
            if (line.contains("|")) {
                line.split("|")
                    .let { (l, r) ->
                        ordering.compute(l.toInt()) { _, v ->
                            if (v == null) {
                                return@compute listOf(r.toInt())
                            }
                            listOf(*v.toTypedArray(), r.toInt())
                        }
                    }
            } else {
                if (line.isNotBlank()) {
                    line.split(",").let {
                        if (it.withIndex().all { (idx, _) ->
                                verifyPosition(it, idx)
                            }) {
                            return@map it[middle(it.size)].toInt()
                        }
                    }
                }
            }
            return@map 0
        }.sum()
    }

    fun part2(input: List<String>): Int {

        return input.map { line ->
            if (line.contains("|")) {
                line.split("|")
                    .let { (l, r) ->
                        ordering.compute(l.toInt()) { _, v ->
                            if (v == null) {
                                return@compute listOf(r.toInt())
                            }
                            listOf(*v.toTypedArray(), r.toInt())
                        }
                    }
            } else {
                if (line.isNotBlank()) {
                    line.split(",").let {
                        if (!it.withIndex().all { (idx, _) ->
                                verifyPosition(it, idx)
                            }) {
                            return@map reorder(it)[middle(it.size)]
                        }
                    }
                }
            }
            return@map 0
        }.sum()
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day05/test_input05")))
    check(part1(readInput("day05/test_input05")) == 143)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}
