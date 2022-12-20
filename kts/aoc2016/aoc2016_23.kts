val input = """cpy a b
dec b
cpy a d
cpy 0 a
cpy b c
inc a
dec c
jnz c -2
dec d
jnz d -5
dec b
cpy b c
cpy c d
dec d
inc c
jnz d -2
tgl c
cpy -16 c
jnz 1 c
cpy 77 c
jnz 73 d
inc a
inc d
jnz d -2
inc c
jnz c -5"""

fun doAssembunny(input: String, registers: MutableMap<Char, Int>): Int {
    val commands = input.split("\n").toMutableList()

    val jnzByValOrValRegex = Regex("""^jnz ([-\d]+) ([-\d]+)$""")
    val jnzByRegOrValRegex = Regex("""^jnz ([a-z]) ([-\d]+)$""")
    val jnzByValOrRegRegex = Regex("""^jnz ([-\d]+) ([a-z])$""")
    val jnzByRegOrRegRegex = Regex("""^jnz ([a-z]) ([a-z])$""")
    val cpyValToRegRegex = Regex("""^cpy ([-\d]+) ([a-z])$""")
    val cpyRegToRegRegex = Regex("""^cpy ([a-z]) ([a-z])$""")
    val cpyRegToValRegex = Regex("""^cpy ([-\d]+) ([-\d]+)$""")
    val cpyValToValRegex = Regex("""^cpy ([a-z]) ([-\d]+)$""")
    val incRegex = Regex("""^inc ([a-z])$""")
    val decRegex = Regex("""^dec ([a-z])$""")
    val tglRegex = Regex("""^tgl ([a-z])$""")

    var i = 0
    while (true) {
        if (i < 0 || i > commands.size - 1) {
            break
        }

        // multiply optimization
        if (
        i + 5 < commands.size - 1 &&
                cpyRegToRegRegex.matches(commands[i]) &&
                incRegex.matches(commands[i + 1]) &&
                decRegex.matches(commands[i + 2]) &&
                jnzByRegOrValRegex.matches(commands[i + 3]) &&
                decRegex.matches(commands[i + 4]) &&
                jnzByRegOrValRegex.matches(commands[i + 5])
                ) {

            val (strRegFrom0, strRegTo0) = cpyRegToRegRegex.matchEntire(commands[i])!!.destructured
            val b0 = strRegFrom0.toCharArray()[0]
            val c0 = strRegTo0.toCharArray()[0]

            val a1 = incRegex.matchEntire(commands[i + 1])!!.destructured.component1().toCharArray()[0]

            val c2 = decRegex.matchEntire(commands[i + 2])!!.destructured.component1().toCharArray()[0]

            val (strReg3, num3) = jnzByRegOrValRegex.matchEntire(commands[i + 3])!!.destructured
            val c3 = strReg3.toCharArray()[0]
            val intStep3 = num3.toInt()

            val d4 = decRegex.matchEntire(commands[i + 4])!!.destructured.component1().toCharArray()[0]

            val (strReg5, num5) = jnzByRegOrValRegex.matchEntire(commands[i + 5])!!.destructured
            val d5 = strReg5.toCharArray()[0]
            val intStep5 = num5.toInt()

            // optimization:
            // 0: cpy b c
            // 1: inc a
            // 2: dec c
            // 3: jnz c -2
            // 4: dec d
            // 5: jnz d -5
            // => a += b * d, c = 0, d = 0
            if (
            intStep3 == -2 && intStep5 == -5 &&
                    c0 == c2 && c2 == c3 &&
                    d4 == d5 &&
                    listOf(/*a*/ a1, /*b*/ b0, /*c*/ c0, /*d*/ d4).toSet().size == 4
                    ) {
                registers[a1] = (registers[a1] ?: 0) + (registers[b0] ?: 0) * (registers[d4] ?: 0)
                registers[c0] = 0
                registers[d4] = 0
                i += 6
                continue
            }
        }

        val command = commands[i]
        when {
            jnzByValOrValRegex.matches(command) -> {
                val (num1, num2) = jnzByValOrValRegex.matchEntire(command)!!.destructured
                val int1 = num1.toInt()
                val int2 = num2.toInt()
                i += if (int1 == 0) 1 else int2
            }
            jnzByRegOrValRegex.matches(command) -> {
                val (strReg, num) = jnzByRegOrValRegex.matchEntire(command)!!.destructured
                val int1 = registers[strReg.toCharArray()[0]] ?: 0
                val int2 = num.toInt()
                i += if (int1 == 0) 1 else int2
            }
            jnzByValOrRegRegex.matches(command) -> {
                val (num, strReg) = jnzByValOrRegRegex.matchEntire(command)!!.destructured
                val int1 = num.toInt()
                val int2 = registers[strReg.toCharArray()[0]] ?: 0
                i += if (int1 == 0) 1 else int2
            }
            jnzByRegOrRegRegex.matches(command) -> {
                val (strReg1, strReg2) = jnzByValOrValRegex.matchEntire(command)!!.destructured
                val int1 = registers[strReg1.toCharArray()[0]] ?: 0
                val int2 = registers[strReg2.toCharArray()[0]] ?: 0
                i += if (int1 == 0) 1 else int2
            }
            cpyValToRegRegex.matches(command) -> {
                val (num, strRegTo) = cpyValToRegRegex.matchEntire(command)!!.destructured
                val regTo = strRegTo.toCharArray()[0]
                registers[regTo] = num.toInt()
                i++
            }
            cpyRegToRegRegex.matches(command) -> {
                val (strRegFrom, strRegTo) = cpyRegToRegRegex.matchEntire(command)!!.destructured
                val regFrom = strRegFrom.toCharArray()[0]
                val regTo = strRegTo.toCharArray()[0]
                registers[regTo] = registers[regFrom] ?: 0
                i++
            }
            cpyRegToValRegex.matches(command) -> {
                i++
            }
            cpyValToValRegex.matches(command) -> {
                i++
            }
            incRegex.matches(command) -> {
                val (strReg) = incRegex.matchEntire(command)!!.destructured
                val reg = strReg.toCharArray()[0]
                registers[reg] = (registers[reg] ?: 0) + 1
                i++
            }
            decRegex.matches(command) -> {
                val (strReg) = decRegex.matchEntire(command)!!.destructured
                val reg = strReg.toCharArray()[0]
                registers[reg] = (registers[reg] ?: 0) - 1
                i++
            }
            tglRegex.matches(command) -> {
                val (strReg) = tglRegex.matchEntire(command)!!.destructured
                val reg = strReg.toCharArray()[0]
                val offset = (registers[reg] ?: 0)
                if (i + offset in 0 until commands.size) {
                    val commandToModify = commands[i + offset]
                    when {
                        jnzByValOrValRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("jnz", "cpy")
                        jnzByRegOrValRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("jnz", "cpy")
                        jnzByValOrRegRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("jnz", "cpy")
                        jnzByRegOrRegRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("jnz", "cpy")
                        cpyValToRegRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("cpy", "jnz")
                        cpyRegToRegRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("cpy", "jnz")
                        cpyRegToValRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("cpy", "jnz")
                        cpyValToValRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("cpy", "jnz")
                        incRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("inc", "dec")
                        decRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("dec", "inc")
                        tglRegex.matches(commandToModify) -> commands[i + offset] = commandToModify.replace("tgl", "inc")
                        else -> throw RuntimeException("Bad command to toggle $commandToModify")
                    }
                }
                i++
            }
            else -> throw RuntimeException("Bad command to execute $command")
        }
    }

    return registers['a'] as Int
}

println("Part One")
println(doAssembunny(input, mutableMapOf('a' to 7, 'b' to 0, 'c' to 0, 'd' to 0)))
println("Part Two")
println(doAssembunny(input, mutableMapOf('a' to 12, 'b' to 0, 'c' to 0, 'd' to 0)))
