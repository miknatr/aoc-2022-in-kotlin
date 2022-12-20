fun doAssembunny(input: String, registers: MutableMap<String, Int>): Int {
    val commands = input.split("\n")

    val jnzValRegex = Regex("""^jnz ([-\d]+) ([-\d]+)$""")
    val jnzRegRegex = Regex("""^jnz ([a-z]) ([-\d]+)$""")
    val cpyValRegex = Regex("""^cpy ([-\d]+) ([a-z])$""")
    val cpyRegRegex = Regex("""^cpy ([a-z]) ([a-z])$""")
    val incRegex = Regex("""^inc ([a-z])$""")
    val decRegex = Regex("""^dec ([a-z])$""")

    var i = 0
    while (true) {
        if (i < 0 || i > commands.size - 1) {
            break
        }
        val command = commands[i]

        when {
            jnzValRegex.matches(command) -> {
                val groups = jnzValRegex.matchEntire(command)!!.groups
                val jump = if (groups[1]!!.value.toInt() == 0) 1 else groups[2]!!.value.toInt()
                i += jump
            }
            jnzRegRegex.matches(command) -> {
                val groups = jnzRegRegex.matchEntire(command)!!.groups
                val jump = if (registers[groups[1]!!.value] == 0) 1 else groups[2]!!.value.toInt()
                i += jump
            }
            cpyValRegex.matches(command) -> {
                val groups = cpyValRegex.matchEntire(command)!!.groups
                registers[groups[2]!!.value] = groups[1]!!.value.toInt()
                i++
            }
            cpyRegRegex.matches(command) -> {
                val groups = cpyRegRegex.matchEntire(command)!!.groups
                registers[groups[2]!!.value] = registers[groups[1]!!.value] ?: 0
                i++
            }
            incRegex.matches(command) -> {
                val groups = incRegex.matchEntire(command)!!.groups
                registers[groups[1]!!.value] = (registers[groups[1]!!.value] ?: 0) + 1
                i++
            }
            decRegex.matches(command) -> {
                val groups = decRegex.matchEntire(command)!!.groups
                registers[groups[1]!!.value] = (registers[groups[1]!!.value] ?: 0) - 1
                i++
            }
        }
    }

    return registers["a"] as Int
}

val input12 = """cpy 1 a
cpy 1 b
cpy 26 d
jnz c 2
jnz 1 5
cpy 7 c
inc d
dec c
jnz c -2
cpy a c
inc a
dec b
jnz b -2
cpy c b
dec d
jnz d -6
cpy 19 c
cpy 11 d
inc a
dec d
jnz d -2
dec c
jnz c -5"""

println("Part One")
val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
println(doAssembunny(input12, registers))
println("Part Two")
val registers2 = mutableMapOf("a" to 0, "b" to 0, "c" to 1, "d" to 0)
println(doAssembunny(input12, registers2))
