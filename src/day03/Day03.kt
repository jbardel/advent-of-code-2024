package day03

import println
import readInput

const val MUL_PREFIX = "mul("
const val MUL_PREFIX_SIZE = MUL_PREFIX.length

const val DO = "do()"
const val DO_SIZE = DO.length

const val DONT = "don't()"
const val DONT_SIZE = DONT.length

var compute = true

fun handlePosition(line: String, index: Int): Int {

    if ((index + MUL_PREFIX_SIZE) < line.length && line.substring(index until index + MUL_PREFIX_SIZE) == MUL_PREFIX) {
        return handleMul(line, index + MUL_PREFIX_SIZE)
    } else if ((index + DO_SIZE) < line.length && line.substring(index until index + DO_SIZE) == DO) {
        handleDo()
    } else if ((index + DONT_SIZE) < line.length && line.substring(index until index + DONT_SIZE) == DONT) {
        handleDont()
    }

    return 0
}

fun handleMul(line: String, index: Int): Int {

    if (!compute) {
        return 0
    }

    val indexComma = line.indexOf(",", index)
    val indexParenthesis = line.indexOf(")", index)

    if (indexParenthesis == -1 || indexComma == -1) {
        return 0
    }

    val opeA = line.substring(index until indexComma).toIntOrNull() ?: return 0
    val opeB = line.substring(indexComma + 1 until indexParenthesis).toIntOrNull() ?: return 0
    return opeA * opeB
}

fun handleDo() {
    compute = true
}

fun handleDont() {
    compute = false
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            it.windowed(4)
                .withIndex()
                .filter { v -> v.value == "mul(" }
                .sumOf { (index, _) -> handleMul(it, index + 4) }
        }
    }

    fun part2(input: List<String>): Int {

        return input.sumOf {
            it.withIndex()
                .sumOf { (index, _) -> handlePosition(it, index) }
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("day03/test_input03")) == 161)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}
