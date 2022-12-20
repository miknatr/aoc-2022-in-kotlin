package aoc2022

fun main() {
    fun IntRange.isFullyContain(range: IntRange) =
        (this.contains(range.first) && this.contains(range.last)) || (range.contains(this.first) && range.contains(this.last))

    fun parseRange(strRange: String): IntRange {
        val numbers = strRange.split("-")
        return numbers[0].toInt()..numbers[1].toInt()
    }

    fun part1(input: List<String>) = input
        .filter { it != "" }
        .map { line -> line.split(",") }
        .map { sections -> Pair(parseRange(sections[0]), parseRange(sections[1])) }
        .filter { it.first.isFullyContain(it.second) }
        .size

    fun part2(input: List<String>) = input
        .filter { it != "" }
        .map { line -> line.split(",") }
        .map { sections -> Pair(parseRange(sections[0]), parseRange(sections[1])) }
        .filter { it.first.intersect(it.second).isNotEmpty() }
        .size

    val testInput = readInput("Day04_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
