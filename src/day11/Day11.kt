package day11

import println
import readInput

interface Rule {

    fun canApply(value: Long): Boolean
    fun apply(value: Long): List<Long>
}

class Rule1 : Rule {

    override fun canApply(value: Long): Boolean = value == 0L

    override fun apply(value: Long): List<Long> = listOf(1)
}

class Rule2 : Rule {

    override fun canApply(value: Long): Boolean = value.toString().length % 2 == 0

    override fun apply(value: Long): List<Long> {
        return value.toString()
            .let {
                val half = it.length / 2
                listOf(it.substring(0 until half).toLong(), it.substring(half until it.length).toLong())
            }
    }
}

class Rule3 : Rule {
    override fun canApply(value: Long): Boolean {
        return true
    }

    override fun apply(value: Long): List<Long> {
        return listOf(value * 2024)
    }

}

val rule1 = Rule1()
val rule2 = Rule2()
val rule3 = Rule3()

fun compute(input: List<String>, times: Int): Long {
    var map = input[0].split(' ').map { it.toLong() }.groupBy { it }.mapValues { it.value.count().toLong() }


    repeat(times) { index ->

        //mapping
        map = map
            .map { (key, value) ->
                when {
                    rule1.canApply(key) -> rule1.apply(key) to value
                    rule2.canApply(key) -> rule2.apply(key) to value
                    rule3.canApply(key) -> rule3.apply(key) to value
                    else -> rule3.apply(key) to value
                }
            }
            .map {
                //Regroup ([1, 7], 1) -> [(1, 1), (7, 1)]
                it.first.map { key ->
                    key to it.second
                }
            }
            .flatten()
            .groupBy { it.first }.mapValues { (_, values) -> values.sumOf { it.second } }
    }

    return map.entries.sumOf { it.value }
}


fun main() {
    fun part1(input: List<String>): Long {
        return compute(input, 25)
    }

    fun part2(input: List<String>): Long {
        return compute(input, 75)
    }

    // Test if implementation meets criteria from the description, like:
    part1(readInput("day11/test_input11")).println()
    check(part1(readInput("day11/test_input11")) == 55312L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day11/Day11")
    part1(input).println()
    part2(input).println()
}
