package day08

import println
import readInput

typealias Position = Pair<Int, Int>

fun Char.isAntenna(): Boolean {
    return this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9'
}

fun List<String>.isIn(position: Position): Boolean {
    return position.first in this.indices && position.second in this.first().indices
}

fun <T> generatePairs(input: List<T>): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()
    for (i in input.indices) {
        for (j in i + 1 until input.size) {
            pairs.add(input[i] to input[j])
        }
    }
    return pairs
}

fun search(input: List<String>): Int {

    return input.asSequence().mapIndexed { idxLine, line ->
        line.mapIndexed { idxCol, char ->
            char to Position(idxLine, idxCol)
        }
    }
        .flatten()
        .filter { (c, _) ->
            c.isAntenna()
        }
        .groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .asSequence()
        .map {
            generatePairs(it.value)
        }
        .flatten()
        .map { (p1, p2) ->
            val x = p1.first - p2.first
            val y = p1.second - p2.second
            if (x < 0) {
                return@map listOf(Pair(p1.first + x, p1.second + y), Pair(p2.first - x, p2.second - y))
            } else {
                return@map listOf(Pair(p1.first - x, p1.second - y), Pair(p2.first + x, p2.second + y))
            }
        }
        .flatten()
        .filter {
            input.isIn(it)
        }
        .distinct()
        .count()
}

fun search2(input: List<String>): Int {
    return input.asSequence().mapIndexed { idxLine, line ->
        line.mapIndexed { idxCol, char ->
            char to Position(idxLine, idxCol)
        }
    }
        .flatten()
        .filter { (c, _) ->
            c.isAntenna()
        }
        .groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .asSequence()
        .map {
            generatePairs(it.value)
        }
        .flatten()
        .map { (p1, p2) ->
            val x = p1.first - p2.first
            val y = p1.second - p2.second
            if (x < 0) {
                return@map listOf(generateAntiNodes(input, p1, x, y), generateAntiNodes(input, p2, -x, -y)).flatten()
            } else {
                return@map listOf(generateAntiNodes(input, p1, -x, -y), generateAntiNodes(input, p2, x, y)).flatten()
            }
        }
        .flatten()
        .sortedBy { it.first }
        .map {
            it
        }
        .filter {
            input.isIn(it)
        }
        .distinct()
        .count()

}

fun generateAntiNodes(
    input: List<String>,
    startPosition: Pair<Int, Int>,
    xSpace: Int,
    ySpace: Int
): List<Pair<Int, Int>> {

    var currentPosition = startPosition
    val positions = mutableListOf<Position>()

    while (input.isIn(currentPosition)) {
        positions.add(currentPosition)
        currentPosition = (currentPosition.first + xSpace) to (currentPosition.second + ySpace)
    }

    return positions
}


fun main() {

    fun part1(input: List<String>): Int {
        return search(input)
    }

    fun part2(input: List<String>): Int {
        return search2(input)
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day08/test_input08")))
    check(part1(readInput("day08/test_input08")) == 14)

    println(part2(readInput("day08/test_input08")))
    check(part2(readInput("day08/test_input08")) == 34)

    val input = readInput("day08/Day08")
    part1(input).println()
    part2(input).println()
}
