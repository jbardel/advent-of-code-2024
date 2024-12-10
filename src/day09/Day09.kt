package day09

import println
import readInput
import java.util.*


private data class Block(val start: Int, val length: Int, val fileId: Long? = null) {
    fun checksum(index: Int = start): Long =
        (0..<length).sumOf {
            (index + it) * (fileId ?: 0L)
        }
}

private fun findAllBlocks(disk: List<Long?>): List<Block> = buildList {
    var blockStart = -1
    var previousValue: Long? = -1L
    disk.withIndex().forEach { (index, value) ->
        if (previousValue == -1L) {
            blockStart = index
            previousValue = value
        } else if (previousValue != value) {
            add(Block(blockStart, index - blockStart, previousValue))
            blockStart = index
            previousValue = value
        }
    }
    if (blockStart != -1) {
        add(Block(blockStart, disk.size - blockStart, previousValue))
    }
}

private fun MutableMap<Int, PriorityQueue<Int>>.findSpace(block: Block): Int =
    (block.length .. 9).mapNotNull { trySize ->
        if (this[trySize]?.isNotEmpty() == true) trySize to this.getValue(trySize).first()
        else null
    }.sortedBy { it.second }.filter { it.second < block.start }.firstNotNullOfOrNull { (blockSize, startAt) ->
        this[blockSize]?.poll()
        if (blockSize != block.length) {
            val diff = blockSize - block.length
            computeIfAbsent(diff) { _ -> PriorityQueue() }.add(startAt + block.length)
        }
        startAt
    } ?: block.start

fun main() {

    fun part1(input: List<String>): Long {
        val disk = input[0]
            .windowed(2, 2, true)
            .withIndex()
            .flatMap { (index, value) ->
                List(value.first().digitToInt()) { _ -> index.toLong() } +
                        List(value.getOrElse(1) { _ -> '0' }.digitToInt()) { null }
            }

        val emptyBlocks = disk.indices.filter { disk[it] == null }.toMutableList()
        return disk.withIndex().reversed().sumOf { (index, value) ->
            if (value != null) {
                value * (emptyBlocks.removeFirstOrNull() ?: index)
            } else {
                emptyBlocks.removeLastOrNull()
                0
            }
        }
    }

    fun part2(input: List<String>): Long {

        val disk = input[0]
            .windowed(2, 2, true)
            .withIndex()
            .flatMap { (index, value) ->
                List(value.first().digitToInt()) { _ -> index.toLong() } +
                        List(value.getOrElse(1) { _ -> '0' }.digitToInt()) { null }
            }
        
        val allBlocks = findAllBlocks(disk)
        val freeSpace: MutableMap<Int, PriorityQueue<Int>> = allBlocks
            .filter { it.fileId == null }
            .groupBy({ it.length }, { it.start })
            .mapValues { (_, v) -> PriorityQueue(v) }
            .toMutableMap()

        return allBlocks.filterNot { it.fileId == null }.reversed().sumOf { block ->
            block.checksum(
                freeSpace.findSpace(block)
            )
        }
    }

    // Test if implementation meets criteria from the description, like:
    println(part1(readInput("day09/test_input09")))
    check(part1(readInput("day09/test_input09")) == 1928.toLong())

    val input = readInput("day09/Day09")
    part1(input).println()
    part2(input).println()
}