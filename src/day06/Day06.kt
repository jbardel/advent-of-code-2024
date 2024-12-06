package day06

import println
import readInput

const val POSITIONS = "^>V<"

fun startPosition(input: List<MutableList<Char>>): Pair<Int, Int> {
    return input.mapIndexed { idxLine, line ->
        line.mapIndexedNotNull { idxCol, c ->
            if (c in POSITIONS) idxLine to idxCol else null
        }
    }.flatten().first()
}

fun nextPosition(input: List<MutableList<Char>>, position: Pair<Int, Int>, direction: Char): Pair<Int, Int> {

    val (moveX, moveY) = when (direction) {
        '^' -> Pair(-1, 0)
        '>' -> Pair(0, 1)
        'V' -> Pair(1, 0)
        '<' -> Pair(0, -1)
        else -> Pair(0, 0)
    }

    var (x, y) = position
    while (true) {
        if ((x + moveX) !in input.indices || (y + moveY) !in input.first().indices) {
            return -1 to -1
        } else if (input[x + moveX][y + moveY] == '#') {
            return x to y
        } else {
            x += moveX
            y += moveY
            input[x][y] = 'X'
        }
    }
}

fun nextDirection(cd: Char): Char {
    var currentIndex = POSITIONS.indexOf(cd)
    return POSITIONS[(currentIndex + 1) % POSITIONS.length]
}

fun countPositions(input: List<MutableList<Char>>): Int {
    return input.flatMap {
        it.toList()
    }.count { it == 'X' }
}

fun main() {

    fun part1(list: List<String>): Int {

        val input = List(list.size) { line ->
            MutableList(list[line].length) { col ->
                list[line][col]
            }
        }

        var cp = startPosition(input)
        var cd = input[cp.first][cp.second]

        while (cp != Pair(-1, -1)) {
            cp = nextPosition(input, cp, cd)
            cd = nextDirection(cd)
        }
        return countPositions(input)
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day06/test_input06")))
    check(part1(readInput("day06/test_input06")) == 41)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day06/Day06")
    part1(input).println()
//    part2(input).println()
}
