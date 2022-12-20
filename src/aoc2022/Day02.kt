package aoc2022

fun main() {
    class Round(
        // A for Rock, B for Paper, and C for Scissors
        val attack: String,
        // X for Rock, Y for Paper, and Z for Scissors
        val defense: String
    ) {
        val scoreForResponse get() = when (defense) {
            "X" -> 1
            "Y" -> 2
            "Z" -> 3
            else -> throw RuntimeException("Unknown defense: $defense")
        }
        val scoreForResult get() = when ("$attack $defense") {
            "A X" -> 3
            "A Y" -> 6
            "A Z" -> 0
            "B X" -> 0
            "B Y" -> 3
            "B Z" -> 6
            "C X" -> 6
            "C Y" -> 0
            "C Z" -> 3
            else -> throw RuntimeException("Unknown attack and defense: $attack $defense")
        }
        val score get() = scoreForResponse + scoreForResult
    }

    class Round2(
        // A for Rock, B for Paper, and C for Scissors
        val attack: String,
        // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win
        val needResult: String
    ) {
        val scoreForResult get() = when (needResult) {
            "X" -> 0
            "Y" -> 3
            "Z" -> 6
            else -> throw RuntimeException("Unknown needResult: $needResult")
        }
        val scoreForResponse get() = when ("$attack $needResult") {
            "A X" -> 3
            "A Y" -> 1
            "A Z" -> 2
            "B X" -> 1
            "B Y" -> 2
            "B Z" -> 3
            "C X" -> 2
            "C Y" -> 3
            "C Z" -> 1
            else -> throw RuntimeException("Unknown attack and defense: $attack $needResult")
        }
        val score get() = scoreForResponse + scoreForResult
    }

    fun part1(input: List<String>) = input
        .filter { it != "" }
        .sumOf { Round(it[0].toString(), it[2].toString()).score }

    fun part2(input: List<String>) = input
        .filter { it != "" }
        .sumOf { Round2(it[0].toString(), it[2].toString()).score }

    val testInput = readInput("Day02_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
