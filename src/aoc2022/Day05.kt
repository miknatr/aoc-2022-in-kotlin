package aoc2022

fun main() {
    class Command(val howMany: Int, val from: Int, val to: Int)

    class Warehouse(
        val initState: List<ArrayDeque<String>>,
        val commands: List<Command>,
    ) {
        val topLetters get() = initState.map { it.last() }.joinToString("")

        fun execute9000() = commands
            .forEach { command ->
                repeat(command.howMany) {
                    val movement = initState[command.from - 1].removeLast()
                    initState[command.to - 1].addLast(movement)
                }
            }

        fun execute9001() = commands
            .forEach { command ->
                val movement = initState[command.from - 1].takeLast(command.howMany)
                initState[command.to - 1].addAll(movement)
                repeat(command.howMany) { initState[command.from - 1].removeLast() }
            }
    }

    fun parseCommands(strCommands: String) = strCommands
        .split("\n")
        .filter { it != "" }
        .map { line -> line.split(" ") }
        // example: move 1 from 8 to 1
        .map { tokens -> Command(tokens[1].toInt(), tokens[3].toInt(), tokens[5].toInt()) }

    fun parseInitState(strInitState: String): List<ArrayDeque<String>> {
        val lines = strInitState.split("\n").reversed()

        val numberStacks = lines.first().trim().split(" ").last().toInt()

        val stacks = List(numberStacks) { ArrayDeque<String>() }

       lines
            .drop(1)
            .forEach { containersLine ->
                containersLine
                    .chunked(4)
                    .map { it.trim(' ', '[', ']') }
                    .withIndex()
                    .forEach {
                        if (it.value.isNotEmpty()) stacks[it.index].addLast(it.value)
                    }
            }

        return stacks
    }

    fun part1(input: String): String {
        val strStateAndCommands= input.split("\n\n")
        val warehouse = Warehouse(parseInitState(strStateAndCommands[0]), parseCommands(strStateAndCommands[1]))
        warehouse.execute9000()
        return warehouse.topLetters
    }

    fun part2(input: String): String {
        val strStateAndCommands= input.split("\n\n")
        val warehouse = Warehouse(parseInitState(strStateAndCommands[0]), parseCommands(strStateAndCommands[1]))
        warehouse.execute9001()
        return warehouse.topLetters
    }

    val testInput = readText("Day05_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readText("Day05")
    part1(input).println()
    part2(input).println()
}
