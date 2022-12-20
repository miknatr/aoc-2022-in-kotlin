val input15 = """Disc #1 has 13 positions; at time=0, it is at position 1.
Disc #2 has 19 positions; at time=0, it is at position 10.
Disc #3 has 3 positions; at time=0, it is at position 2.
Disc #4 has 7 positions; at time=0, it is at position 1.
Disc #5 has 5 positions; at time=0, it is at position 3.
Disc #6 has 17 positions; at time=0, it is at position 5."""

val input15_2 = """$input15
Disc #7 has 11 positions; at time=0, it is at position 0."""

data class Disc(val level: Int, val positions: Int, val startPosition: Int)

fun parseInput(input: String): List<Disc> =
    input
        .split("\n")
        .map { Regex("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).").matchEntire(it)!! }
        .filterNotNull()
        .map { it.groups }
        .map { Disc(it[1]!!.value.toInt(), it[2]!!.value.toInt(), it[3]!!.value.toInt()) }

fun canFallThroughDisk(disk: Disc, second: Int): Boolean =
    (second + disk.level + disk.startPosition) % disk.positions == 0

fun getSecondToFall(disks: List<Disc>): Int =
    generateSequence(0, Int::inc)
        .filter { second -> disks.all { disk -> canFallThroughDisk(disk, second) } }
        .first()

println("Part One")
println(getSecondToFall(parseInput(input15)))
println("Part Two")
println(getSecondToFall(parseInput(input15_2)))
