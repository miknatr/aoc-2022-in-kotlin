fun solve(input: String, extraOnFirstFloor: Int): Int {
    val floorItems = input.split("\n").map { it.split(" a ").count() - 1 }.toMutableList()
    floorItems[0] += extraOnFirstFloor
    return (1..floorItems.size - 1).map { floorItems.subList(0, it).sum() * 2 - 3 }.sum()
}

val input11 = """The first floor contains a thulium generator, a thulium-compatible microchip, a plutonium generator, and a strontium generator.
The second floor contains a plutonium-compatible microchip and a strontium-compatible microchip.
The third floor contains a promethium generator, a promethium-compatible microchip, a ruthenium generator, and a ruthenium-compatible microchip.
The fourth floor contains nothing relevant."""

println("Part One")
println(solve(input11, 0))
println("Part Two")
println(solve(input11, 4))
