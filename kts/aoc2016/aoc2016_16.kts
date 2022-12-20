fun getData(input: String, maxLen: Int): String {
    val next = { s: String -> s + '0' + s.reversed().map { if (it == '1') '0' else '1' }.joinToString("") }
    val data = generateSequence(next(input), next).dropWhile { it.length < maxLen }.first().substring(0, maxLen)
    val check = { s: String -> (0..s.length - 1 step 2).map { s.substring(it, it + 2) }.map { if (it[0] == it[1]) "1" else "0" }.joinToString("") }
    return generateSequence(check(data), check).dropWhile { it.length % 2 != 1 }.first()
}

println("Part One")
println(getData("11011110011011101", 272))
println("Part Two")
println(getData("11011110011011101", 35651584))
