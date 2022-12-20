fun main() {
    fun elves(input: List<String>): MutableList<Int> {
        val elves = mutableListOf<Int>()
        var currentElf = 0

        input.forEach {
            if (it == "") {
                elves.add(currentElf)
                currentElf = 0
                return@forEach
            }

            currentElf += it.toInt()
        }

        elves.add(currentElf)

        return elves
    }

    fun part1(input: List<String>) = elves(input)
        .max()

    fun part2(input: List<String>) = elves(input)
        .sortedDescending()
        .take(3)
        .sum()

    val testInput = readInput("Day01_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
