package day08

import println
import readInput

typealias Position = Pair<Int, Int>

fun Char.isAntenna(): Boolean {
    return this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9'
}

fun searchNextAntennas(char: Char, position: Position): List<Position> {

    return listOf(0 to 0)
}

fun doIt(input: List<String>) {

    input.mapIndexed { idxLine, line ->
        line.mapIndexed { idxCol, char -> char to Position(idxLine, idxCol) }
    }


    for ((idxLine, line) in input.withIndex()) {
        for ((idxCol, char) in line.withIndex()) {
            if (char.isAntenna()) {

            }
        }
    }

}


fun main() {

    fun part1(input: List<String>): Long {


        return 0
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day08/test_input08")))
    check(part1(readInput("day08/test_input08")) == 3749.toLong())

    val input = readInput("day08/Day08")
    part1(input).println()
    part2(input).println()
}
