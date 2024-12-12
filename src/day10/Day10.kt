package day10

import println
import readInput

typealias Position = Pair<Int, Int>

val NEIGHBORS = listOf(0 to -1, 1 to 0, 0 to 1, -1 to 0)

infix fun Position.isIn(input: List<String>): Boolean {
    return this.first in input.indices && this.second in input.first().indices
}

private operator fun Position.plus(neighbor: Position): Position {
    return Pair(this.first + neighbor.first, this.second + neighbor.second)
}

fun findTrails(input: List<String>, peaks: MutableCollection<Position>, position: Position, value: Int) {

    for (neighbor in NEIGHBORS) {
        val nextPosition = position + neighbor
        val (idxLine, idxCol) = nextPosition

        if (nextPosition isIn input) {
            if (input[idxLine][idxCol].toString().toInt() == value) {
                if (value == 9) {
                    peaks.add(nextPosition)
                } else if (value < 9) {
                    findTrails(input, peaks, nextPosition, value + 1)
                }
            }
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {

        val peaks = mutableSetOf<Position>()
        var peaksCount = 0
        for ((idxLine, line) in input.withIndex()) {
            for ((idxCol, char) in line.withIndex()) {
                if (char == '0') {
                    findTrails(input, peaks, idxLine to idxCol, 1)
                    peaksCount += peaks.size
                    peaks.clear()
                }
            }
        }
        return peaksCount
    }

    fun part2(input: List<String>): Int {

        val peaks = mutableListOf<Position>()
        var peaksCount = 0
        for ((idxLine, line) in input.withIndex()) {
            for ((idxCol, char) in line.withIndex()) {
                if (char == '0') {
                    findTrails(input, peaks, idxLine to idxCol, 1)
                    peaksCount += peaks.size
                    peaks.clear()
                }
            }
        }
        
        return peaksCount
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day10/test_input10")))
    check(part1(readInput("day10/test_input10")) == 36)

    val input = readInput("day10/Day10")
    part1(input).println()
    part2(input).println()
}