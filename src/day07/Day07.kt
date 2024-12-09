package day07

import println
import readInput

data class Node(
    val value: Long,
    val ope: Operation,
    val add: Node?,
    val mul: Node?,
    val concat: Node?
)

enum class Operation {
    ADD, MUL, CONCAT, NOOP
}

fun buildTree(operands: List<Long>, ope: Operation): Node {

    if (operands.size == 1) {
        return Node(operands.first(), ope, null, null, null)
    }
    return Node(
        operands.first(),
        ope,
        buildTree2(operands.subList(1, operands.size), Operation.ADD),
        buildTree2(operands.subList(1, operands.size), Operation.MUL),
        null
    )
}

fun buildTree2(operands: List<Long>, ope: Operation): Node {

    if (operands.size == 1) {
        return Node(operands.first(), ope, null, null, null)
    }
    return Node(
        operands.first(),
        ope,
        buildTree2(operands.subList(1, operands.size), Operation.ADD),
        buildTree2(operands.subList(1, operands.size), Operation.MUL),
        buildTree2(operands.subList(1, operands.size), Operation.CONCAT)
    )
}

fun readTree2(node: Node?, current: Long = 0): List<Long> {

    if(node == null){
        return listOf()
    }

    val newCurrent = when (node.ope) {
        Operation.ADD -> current + node.value
        Operation.MUL -> current * node.value
        Operation.CONCAT -> (current.toString() + node.value.toString()).toLong()
        Operation.NOOP -> node.value
    }

    if (node.add == null && node.mul == null) {
        return listOf(newCurrent)
    }

    return readTree2(node.add, newCurrent) + readTree2(node.mul, newCurrent)+ readTree2(node.concat, newCurrent)
}

fun readTree(node: Node?, current: Long = 0): List<Long> {

    if(node == null){
        return listOf()
    }

    val newCurrent = when (node.ope) {
        Operation.ADD -> current + node.value
        Operation.MUL -> current * node.value
        Operation.NOOP -> node.value
        else -> node.value
    }

    if (node.add == null && node.mul == null) {
        return listOf(newCurrent)
    }

    return readTree(node.add, newCurrent) + readTree(node.mul, newCurrent)
}

operator fun Pair<Long, Long>.plus(right: Pair<Long, Long>): Pair<Long, Long> {
    val (la, lb) = this
    val (ra, rb) = right
    return (la + ra) to (lb + rb)
}

fun handleLine2(line: String): Long {

    val result = line.substring(0, line.indexOf(':')).trim().toLong()
    val operands = line.substring(line.indexOf(':') + 1).trim().split(" ").map { it.toLong() }

    val rootNode = buildTree2(operands, Operation.NOOP)
    val ret = readTree2(rootNode)

    return if (ret.contains(result)) result else 0
}


fun handleLine(line: String): Long {

    val result = line.substring(0, line.indexOf(':')).trim().toLong()
    val operands = line.substring(line.indexOf(':') + 1).trim().split(" ").map { it.toLong() }

    val rootNode = buildTree(operands, Operation.NOOP)
    val ret = readTree(rootNode)

    return if (ret.contains(result)) result else 0
}


fun main() {

    fun part1(input: List<String>): Long {
        return input.sumOf { handleLine(it) }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { handleLine2(it) }
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day07/test_input07")))
    check(part1(readInput("day07/test_input07")) == 3749.toLong())

    val input = readInput("day07/Day07")
    part1(input).println()
    part2(input).println()
}
