package day02

import println
import readInput

fun isSafe(l: List<Int>): Boolean {
    val cr = l[0] - l[1] < 0
    for (e in l.zipWithNext()) {
        if (cr && (e.first - e.second) !in -3..-1) {
            return false
        } else if (!cr && (e.first - e.second) !in 1..3) {
            return false
        }
    }
    return true
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            it.split(" ").map(String::toInt)
        }.map {
            isSafe(it)
        }.partition {
            it
        }.first.count()
    }

    fun part2(input: List<String>): Int {
        return input.map {
            it.split(" ").map(String::toInt)
        }.map {
            if (isSafe(it)) {
                true
            } else {
                for (i in 0 until it.count()) {
                    if (isSafe(it.filterIndexed { index, _ -> index != i })) {
                        return@map true
                    }
                }
                false
            }
        }.count { it }
    }

// Test if implementation meets criteria from the description, like:
    check(part1(readInput("day02/test_input02")) == 2)
    check(part2(readInput("day02/test_input02_part2")) == 4)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}
