package day04

import println
import readInput

val positions = mapOf(
    (-1 to -1) to (1 to 1),
    (-1 to 1) to (1 to -1),
    (1 to 1) to (-1 to -1),
    (1 to -1) to (-1 to 1)
)

fun findXMAS(input: List<String>, idxLine: Int, idxCol: Int): Int {
    var count = 0
    for (i in idxLine - 1..idxLine + 1) {
        for (j in idxCol - 1..idxCol + 1) {
            if (i in input.indices && j in input[0].indices && input[i][j] == 'M') {
                if (findXMAS(input, i, j, i - idxLine, j - idxCol)) {
                    count++
                }
            }
        }
    }
    return count
}

fun findXMAS2(input: List<String>, idxLine: Int, idxCol: Int): Boolean {
    return positions.count { (mPos, sPos) ->
        val (mLine, mCol) = idxLine + mPos.first to idxCol + mPos.second
        val (sLine, sCol) = idxLine + sPos.first to idxCol + sPos.second

        fun isInBounds(line: Int, col: Int) = line in input.indices && col in input.first().indices

        isInBounds(mLine, mCol) &&
                isInBounds(sLine, sCol) &&
                input[mLine][mCol] == 'M' &&
                input[sLine][sCol] == 'S'
    } == 2
}

fun findXMAS(input: List<String>, idxLine: Int, idxCol: Int, dirLine: Int, dirCol: Int): Boolean {

    var currentLine = idxLine
    var currentCol = idxCol

    for (currentChar in "AS") {
        currentLine += dirLine
        currentCol += dirCol
        if (currentLine !in input.indices || currentCol !in input.first().indices || input[currentLine][currentCol] != currentChar) {
            return false
        }
    }
    return true
}

fun main() {
    fun part1(input: List<String>): Int {

        var c = 0
        for ((idxLine, line) in input.withIndex()) {
            for ((idxCol, char) in line.withIndex()) {
                if (char == 'X') {
                    c += findXMAS(input, idxLine, idxCol)
                }
            }
        }
        return c
    }

    fun part2(input: List<String>): Int {

        var c = 0
        for ((idxLine, line) in input.withIndex()) {
            for ((idxCol, char) in line.withIndex()) {
                if (char == 'A' && findXMAS2(input, idxLine, idxCol)) {
                    c++
                }
            }
        }
        return c
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("day04/test_input04")) == 18)
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}
