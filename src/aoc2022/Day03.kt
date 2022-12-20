package aoc2022

fun main() {
    println('A'.code)
    println('Z'.code)
    println('a'.code)
    println('z'.code)

    fun Char.getPriority(): Int = if (this.code >= 97) this.code - 96 else this.code - 65 + 27

    fun part1(input: List<String>) = input
        .filter { it != "" }
        .map { line ->
            Pair(
                line
                    .slice(0 until line.length / 2)
                    .toSet(),
                line
                    .slice(line.length / 2 until line.length)
                    .toSet()
            )
        }
        .map { backpack ->
            backpack.first
                .intersect(backpack.second)
                .first()
        }
        .sumOf { onlyItem -> onlyItem.getPriority() }


    fun part2(input: List<String>) = input
        .filter { it != "" }
        .chunked(3)
        .map { elvesTrio ->
            elvesTrio[0].toSet()
                .intersect(elvesTrio[1].toSet())
                .intersect(elvesTrio[2].toSet())
                .first()
        }
        .sumOf { onlyItem -> onlyItem.getPriority() }

    val testInput = readInput("Day03_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
