val input = """rotate left 2 instructions
rotate right 0 instructions
rotate based on position of letter a
rotate based on position of letter f
swap letter g with letter b
rotate left 4 instructions
swap letter e with letter f
reverse positions 1 through 6
swap letter b with letter d
swap letter b with letter c
move position 7 to position 5
rotate based on position of letter h
swap position 6 with position 5
reverse positions 2 through 7
move position 5 to position 0
rotate based on position of letter e
rotate based on position of letter c
rotate right 4 instructions
reverse positions 3 through 7
rotate left 4 instructions
rotate based on position of letter f
rotate left 3 instructions
swap letter d with letter a
swap position 0 with position 1
rotate based on position of letter a
move position 3 to position 6
swap letter e with letter g
move position 6 to position 2
reverse positions 1 through 2
rotate right 1 step
reverse positions 0 through 6
swap letter e with letter h
swap letter f with letter a
rotate based on position of letter a
swap position 7 with position 4
reverse positions 2 through 5
swap position 1 with position 2
rotate right 0 instructions
reverse positions 5 through 7
rotate based on position of letter a
swap letter f with letter h
swap letter a with letter f
rotate right 4 instructions
move position 7 to position 5
rotate based on position of letter a
reverse positions 0 through 6
swap letter g with letter c
reverse positions 5 through 6
reverse positions 3 through 5
reverse positions 4 through 6
swap position 3 with position 4
move position 4 to position 2
reverse positions 3 through 4
rotate left 0 instructions
reverse positions 3 through 6
swap position 6 with position 7
reverse positions 2 through 5
swap position 2 with position 0
reverse positions 0 through 3
reverse positions 3 through 5
rotate based on position of letter d
move position 1 to position 2
rotate based on position of letter c
swap letter e with letter a
move position 4 to position 1
reverse positions 5 through 7
rotate left 1 step
rotate based on position of letter h
reverse positions 1 through 7
rotate based on position of letter f
move position 1 to position 5
reverse positions 1 through 4
rotate based on position of letter a
swap letter b with letter c
rotate based on position of letter g
swap letter a with letter g
swap position 1 with position 0
rotate right 2 instructions
rotate based on position of letter f
swap position 5 with position 4
move position 1 to position 0
swap letter f with letter b
swap letter f with letter h
move position 1 to position 7
swap letter c with letter b
reverse positions 5 through 7
rotate left 6 instructions
swap letter d with letter b
rotate left 3 instructions
swap position 1 with position 4
rotate based on position of letter a
rotate based on position of letter a
swap letter b with letter c
swap letter e with letter f
reverse positions 4 through 7
rotate right 0 instructions
reverse positions 2 through 3
rotate based on position of letter a
reverse positions 1 through 4
rotate right 1 step"""

fun swapPosition(input: String, x: Int, y: Int): String {
    val arr = input.toCharArray()
    val tmp = input[x]
    arr[x] = arr[y]
    arr[y] = tmp
    return arr.joinToString("")
}

fun swapLetters(input: String, x: Char, y: Char): String = input.map { if (it == x) y else if (it == y) x else it }.joinToString("")

fun rotateShift(input: String, direction: String, steps: Int): String {
    val effSteps = steps % input.length
    if (direction == "left") {
        return input.drop(effSteps) + input.take(effSteps)
    }
    return input.drop(input.length - effSteps).take(effSteps) + input.take(input.length - effSteps)
}

fun rotateLetter(input: String, ch: Char, forward: Boolean): String {
    if (forward) {
        return rotateShift(input, "right", listOf(1, 2, 3, 4, 6, 7, 8, 9)[input.indexOf(ch)])
    }
    return rotateShift(input, "left", listOf(9, 1, 6, 2, 7, 3, 8, 4)[input.indexOf(ch)])
}

fun reverse2(input: String, x: Int, y: Int): String = input.substring(0, x) + input.drop(x).take(y - x + 1).reversed() + input.drop(y + 1)

fun move(input: String, x: Int, y: Int): String {
    val wox = input.substring(0, x) + input.substring(x + 1)
    return wox.substring(0, y) + input[x] + wox.substring(y)
}

abstract class Processor {
    fun match(command: String) = getRegex().matches(command)
    abstract fun getRegex(): Regex
    abstract fun process(str: String, command: String): String
}

inner class SwapByPos : Processor() {
    override fun getRegex() = Regex("""^swap position (\d+) with position (\d+)$""")
    override fun process(str: String, command: String): String {
        val (x, y) = getRegex().matchEntire(command)!!.destructured
        return swapPosition(str, x.toInt(), y.toInt())
    }
}

inner class SwapByLetter : Processor() {
    override fun getRegex() = Regex("""^swap letter (\S) with letter (\S)$""")
    override fun process(str: String, command: String): String {
        val (x, y) = getRegex().matchEntire(command)!!.destructured
        return swapLetters(str, x[0], y[0])
    }
}

open inner class RotateByShift : Processor() {
    override fun getRegex() = Regex("""^rotate (left|right) (\d+) step(s?)$""")
    override fun process(str: String, command: String): String {
        val (dir, x) = getRegex().matchEntire(command)!!.destructured
        return rotateShift(str, dir, x.toInt())
    }
}

inner class RotateByShiftRev : RotateByShift() {
    override fun process(str: String, command: String): String {
        val (dir, x) = getRegex().matchEntire(command)!!.destructured
        return rotateShift(str, if (dir == "left") "right" else "left", x.toInt())
    }
}

open inner class RotateByLetter : Processor() {
    override fun getRegex() = Regex("""^rotate based on position of letter (\S)$""")
    override fun process(str: String, command: String): String {
        val (x) = getRegex().matchEntire(command)!!.destructured
        return rotateLetter(str, x[0], true)
    }
}

inner class RotateByLetterRev : RotateByLetter() {
    override fun process(str: String, command: String): String {
        val (x) = getRegex().matchEntire(command)!!.destructured
        return rotateLetter(str, x[0], false)
    }
}

inner class Reverse : Processor() {
    override fun getRegex() = Regex("""^reverse positions (\d+) through (\d+)$""")
    override fun process(str: String, command: String): String {
        val (x, y) = getRegex().matchEntire(command)!!.destructured
        return reverse2(str, x.toInt(), y.toInt())
    }
}

open inner class Move : Processor() {
    override fun getRegex() = Regex("""^move position (\d+) to position (\d+)$""")
    override fun process(str: String, command: String): String {
        val (x, y) = getRegex().matchEntire(command)!!.destructured
        return move(str, x.toInt(), y.toInt())
    }
}

inner class MoveRev : Move() {
    override fun process(str: String, command: String): String {
        val (x, y) = getRegex().matchEntire(command)!!.destructured
        return move(str, y.toInt(), x.toInt())
    }
}

fun process(str: String, command: String, processors: List<Processor>): String {
    val processor = processors.firstOrNull { it.match(command) }
    if (processor == null) {
        throw RuntimeException("Processor not found for $command")
    }
    return processor.process(str, command)
}

fun processAll(str: String, commands: String): String {
    val processors = listOf(SwapByPos(), SwapByLetter(), RotateByShift(), RotateByLetter(), Reverse(), Move())

    return commands
            .split("\n")
            .fold(str) { carry, command -> process(carry, command, processors) }
}
fun processAllRev(str: String, commands: String): String {
    val processors = listOf(SwapByPos(), SwapByLetter(), RotateByShiftRev(), RotateByLetterRev(), Reverse(), MoveRev())

    return commands
            .split("\n")
            .reversed()
            .fold(str) { carry, command -> process(carry, command, processors) }
}

println("Part One")
println(processAll("abcdefgh", input))
println("Part Two")
println(processAllRev("fbgdceah", input))
