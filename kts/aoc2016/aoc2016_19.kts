fun jackpotElf(elfNum: Int): Int {
    val elves = java.util.ArrayDeque((1..elfNum).toList())
    while (elves.size > 1) {
        elves.addLast(elves.removeFirst())
        elves.removeFirst()
    }
    return elves.first()
}

fun realJackpotElf(elfNum: Int): Int {
    val firstHalfElves = java.util.ArrayDeque((1..elfNum / 2).toList())
    val secondHalfElves = java.util.ArrayDeque((elfNum / 2 + 1..elfNum).toList())

    while (firstHalfElves.size + secondHalfElves.size > 1) {
        if (firstHalfElves.size > secondHalfElves.size) {
            firstHalfElves.removeLast()
        } else {
            secondHalfElves.removeFirst()
        }
        secondHalfElves.addLast(firstHalfElves.removeFirst())
        firstHalfElves.addLast(secondHalfElves.removeFirst())
    }

    return if (firstHalfElves.size == 1) firstHalfElves.first() else secondHalfElves.first()
}

println("Part One")
println(jackpotElf(3001330))
println("Part Two")
println(realJackpotElf(3001330))
