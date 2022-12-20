val input = """.^^^.^.^^^.^.......^^.^^^^.^^^^..^^^^^.^.^^^..^^.^.^^..^.^..^^...^.^^.^^^...^^.^.^^^..^^^^.....^...."""

fun safeTiles(inp: String, rows: Int) =
        generateSequence(inp, { str ->
            (0..".$str.".length - 3).map { if (".$str."[it] != ".$str."[it + 2]) '^' else '.' }.joinToString("")
        })
        .take(rows)
        .map { it.count { it == '.' } }
        .sum()

println("Part One")
println(safeTiles(input, 40))
println("Part Two")
println(safeTiles(input, 400000))
