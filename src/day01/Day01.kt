package day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val (listA, listB) = input.map {
            it.split("   ")
        }.let {
            Pair(
                it.map { parts -> parts[0].toInt() }.sorted(),
                it.map { parts -> parts[1].toInt() }.sorted()
            )
        }

        return listA.withIndex().sumOf {
            abs(it.value - listB[it.index])
        }
    }

    fun part2(input: List<String>): Int {

        val (listA, counts) = input.map {
            it.split("   ")
        }.let {
            Pair(
                it.map { parts -> parts[0].toInt() }.sorted(),
                it.map { parts -> parts[1].toInt() }.sorted()
            )
        }.let {
            Pair(
                it.first,
                it.second.groupBy { e ->
                    e
                }.mapValues { e ->
                    e.value.size
                })
        }

        return listA.sumOf {
            it * (counts[it] ?: 0)
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("day01/test_input01")) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}
