package day06

import println
import readInput

const val POSITIONS = "^>V<"

typealias Room = List<MutableList<Char>>
typealias GuardState = Pair<Int, Int>

fun startPosition(input: Room): Pair<Int, Int> {
    return input.mapIndexed { idxLine, line ->
        line.mapIndexedNotNull { idxCol, c ->
            if (c in POSITIONS) idxLine to idxCol else null
        }
    }.flatten().first()
}

fun nextDirection(cd: Char): Char {
    val currentIndex = POSITIONS.indexOf(cd)
    return POSITIONS[(currentIndex + 1) % POSITIONS.length]
}

fun isOutside(input: Room, position: Pair<Int, Int>): Boolean {
    return position.first !in input.indices || position.second !in input.first().indices
}

fun isWall(input: Room, position: Pair<Int, Int>): Boolean {
    return input[position.first][position.second] == '#'
}

fun copy(input: List<String>, position: Pair<Int, Int>): Room {
    return List(input.size) { line ->
        MutableList(input[line].length) { col ->
            if (line == position.first && col == position.second && input[line][col] !in POSITIONS)
                '#'
            else input[line][col]
        }
    }
}

fun nextPosition(
    input: Room,
    position: Pair<Int, Int>,
    direction: Char,
    visited: MutableSet<GuardState>
): Pair<Int, Int> {
    val (moveX, moveY) = when (direction) {
        '^' -> Pair(-1, 0)
        '>' -> Pair(0, 1)
        'V' -> Pair(1, 0)
        '<' -> Pair(0, -1)
        else -> Pair(0, 0)
    }

    var (x, y) = position
    while (true) {
        if (isOutside(input, (x + moveX) to (y + moveY))) {
            return -1 to -1
        } else if (isWall(input, (x + moveX) to (y + moveY))) {
            return x to y
        } else {
            x += moveX
            y += moveY

            visited.add(x to y)
        }
    }
}

fun computeGuardPatrol(input: Room): Boolean {

    val visited = mutableSetOf<GuardState>()
    val startPosition = startPosition(input)
    var cp = startPosition
    var cd = input[cp.first][cp.second]

    while (cp != Pair(-1, -1)) {
        cp = nextPosition(input, cp, cd, visited)
        cd = nextDirection(cd)

        if (cp == startPosition) {
            return true
        }
    }
    return false
}


fun main() {

    fun part1(list: List<String>): Int {

        val input = List(list.size) { line ->
            MutableList(list[line].length) { col ->
                list[line][col]
            }
        }
        val visited = mutableSetOf<GuardState>()
        var cp = startPosition(input)
        var cd = input[cp.first][cp.second]

        while (cp != Pair(-1, -1)) {
            cp = nextPosition(input, cp, cd, visited)
            cd = nextDirection(cd)
        }
        return visited.size
    }

    fun part2(list: List<String>): Int {

        //On parcours chaque element et on fait une modification
        // Si on revient sur le point départ dans la meme direction, c'est une boucle
        for ((idxLine, line) in list.withIndex()) {
            for ((idxCol, _) in line.withIndex()) {
                val input = copy(list, idxLine to idxCol)
                println("Computing for index ($idxLine, $idxCol)")
                computeGuardPatrol(input)
            }
        }

        return 0
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day06/test_input06")))
    check(part1(readInput("day06/test_input06")) == 41)

    // Read the input from the `src/Day01.txt` file.
    //println(part2(readInput("day06/test_input06")))
    //check(part2(readInput("day06/test_input06")) == 6)


    val input = readInput("day06/Day06")
    part1(input).println()
    //part2(input).println()
}
