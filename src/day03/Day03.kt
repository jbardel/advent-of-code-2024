package day03

import println
import readInput

fun compute(line: String, index: Int): Int {

    val indexParenthesis = line.lastIndexOf(")", index)

    if(indexParenthesis == -1){
        return 0
    }

    for(c in line.substring(index until index + indexParenthesis)){
        println(c)
    }

    return 0
}


fun main() {
    fun part1(input: List<String>): Int {

        for (line in input) {
            for (str in line.windowed(4).withIndex()) {
                if (str.value == "mul(") {
                    compute(line, str.index)
                }
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("day03/test_input03")) == 161)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}
