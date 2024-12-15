package day12

import println
import readInput

typealias Position = Pair<Int, Int>
typealias Count = Pair<Int, Int> //Perimeter - Surface

val NEIGHBORS = listOf(0 to -1, 1 to 0, 0 to 1, -1 to 0)

infix fun Position.isInList(input: List<String>): Boolean {
    return this.first in input.indices && this.second in input.first().indices
}

private operator fun Position.plus(neighbor: Position): Position {
    return Pair(this.first + neighbor.first, this.second + neighbor.second)
}

fun findArea(input: List<String>, visited: MutableList<Position>, position: Position, value: Char): Count {

    val (line, col) = position

    if (visited.contains(position)) {
        return 0 to 0
    } else if (!(position isInList input) || input[line][col] != value) {
        return 1 to 0
    }

    visited.add(position)

    var count = 0 to 1
    for (neighbor in NEIGHBORS) {
        count += findArea(input, visited, position + neighbor, value)
    }

    return count
}

fun main() {
    fun part1(input: List<String>): Int {

        val visited = mutableListOf<Position>()
        var count = 0

        for ((idxLine, line) in input.withIndex()) {
            for ((idxCol, char) in line.withIndex()) {
                val localVisited = mutableListOf<Position>()
                if (!visited.contains(idxLine to idxCol)) {
                    count += findArea(
                        input,
                        localVisited,
                        idxLine to idxCol,
                        char
                    ).let { (perimeter, area) -> perimeter * area }
                }
                visited.addAll(localVisited)
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Test if implementation meets criteria from the description, like:
    part1(readInput("day12/test_input12")).println()
    check(part1(readInput("day12/test_input12")) == 1930)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day12/Day12")
    part1(input).println()
    part2(input).println()
}
