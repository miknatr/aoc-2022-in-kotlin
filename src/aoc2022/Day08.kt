package aoc2022

import kotlin.math.max

fun main() {
    fun parseGrid(input: List<String>) = input
        .filter { it != "" }
        .map { it.map { char -> char.digitToInt() } }

    fun List<List<Int>>.countVisibleTrees(): Int {
        val gridSize = first().size

        var invisibleCount = 0

        withIndex().forEach { (x, line) ->
            line.withIndex().forEach inner@{ (y, height) ->
                val lineBefore = (0 until x)
                if (lineBefore.isEmpty()) return@inner
                if (height > lineBefore.map { this[it][y] }.max()) return@inner

                val lineAfter = (x + 1 until gridSize)
                if (lineAfter.isEmpty()) return@inner
                if (height > lineAfter.map { this[it][y] }.max()) return@inner

                val columnBefore = (0 until y)
                if (columnBefore.isEmpty()) return@inner
                if (height > columnBefore.map { this[x][it] }.max()) return@inner

                val columnAfter = (y + 1 until gridSize)
                if (columnAfter.isEmpty()) return@inner
                if (height > columnAfter.map { this[x][it] }.max()) return@inner

                invisibleCount++
            }
        }

        return gridSize * gridSize - invisibleCount
    }

    fun List<List<Int>>.getScore(x: Int, y: Int): Int {
        val gridSize = first().size
        val height = this[x][y]

        var lineBeforeScore = 0
        for (i in x - 1 downTo 0) {
            if (this[i][y] >= height) {
                lineBeforeScore++
                break
            }
            lineBeforeScore++
        }

        var lineAfterScore = 0
        for (i in x + 1 until gridSize) {
            if (this[i][y] >= height) {
                lineAfterScore++
                break
            }
            lineAfterScore++
        }

        var columnBeforeScore = 0
        for (i in y - 1 downTo 0) {
            if (this[x][i] >= height) {
                columnBeforeScore++
                break
            }
            columnBeforeScore++
        }

        var columnAfterScore = 0
        for (i in y + 1 until gridSize) {
            if (this[x][i] >= height) {
                columnAfterScore++
                break
            }
            columnAfterScore++
        }

        return lineBeforeScore * lineAfterScore * columnBeforeScore * columnAfterScore
    }

    fun part1(input: List<String>) = parseGrid(input).countVisibleTrees()

    fun part2(input: List<String>): Int {
        val grid = parseGrid(input)

        var maxScore = 0

        grid.withIndex().forEach { (x, line) ->
            line.withIndex().forEach { (y, _) ->
                maxScore = max(grid.getScore(x, y), maxScore)
            }
        }

        return maxScore
    }

    val testInput = readInput("Day08_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
