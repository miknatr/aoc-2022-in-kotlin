fun main() {
    fun part1(input: List<String>) = input
        .filter { it != "" }
        .map { line ->
            var lastFour = line.substring(0..3)
            for (i in 4 until line.length) {
                if (lastFour.toList().distinct().size == 4) {
                    return@map i
                }
                lastFour = lastFour.substring(1)
                lastFour += line[i]
            }
        }

    fun part2(input: List<String>) = input
        .filter { it != "" }
        .map { line ->
            var lastFour = line.substring(0..13)
            for (i in 14 until line.length) {
                if (lastFour.toList().distinct().size == 14) {
                    return@map i
                }
                lastFour = lastFour.substring(1)
                lastFour += line[i]
            }
        }

    val testInput = readInput("Day06_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
