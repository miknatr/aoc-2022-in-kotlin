class PinCodeDisplay {
    val rowWidth = 50
    val columnHeight = 6
    var initDisplay = Array(columnHeight, { i -> Array(rowWidth, { false }) })

    fun countLid(input: String): Int {
        return doCommands(input).fold(0, { acc, booleans -> acc + booleans.count { it } })
    }

    fun code(input: String): String {
        return doCommands(input).map { it.map { if (it) "#" else " " }.joinToString("") }.joinToString("\n")
    }

    fun doCommands(input: String): Array<Array<Boolean>> {
        val curDisplay = initDisplay.copyOf()

        for (command in input.split("\n")) {
            when {
                command.startsWith("rect") -> {
                    val groups = Regex("rect ([0-9]+)x([0-9]+)").matchEntire(command)!!.groups
                    val x = groups[1]!!.value.toInt()
                    val y = groups[2]!!.value.toInt()
                    rect(curDisplay, x, y)
                }
                command.startsWith("rotate row") -> {
                    val groups = Regex("rotate row y=([0-9]+) by ([0-9]+)").matchEntire(command)!!.groups
                    val y = groups[1]!!.value.toInt()
                    val shift = groups[2]!!.value.toInt()
                    rotateRow(curDisplay, y, shift)
                }
                command.startsWith("rotate column") -> {
                    val groups = Regex("rotate column x=([0-9]+) by ([0-9]+)").matchEntire(command)!!.groups
                    val x = groups[1]!!.value.toInt()
                    val shift = groups[2]!!.value.toInt()
                    rotateColumn(curDisplay, x, shift)
                }
            }
        }

        return curDisplay
    }

    fun rect(display: Array<Array<Boolean>>, xLid: Int, yLid: Int) {
        for (y in 0 until yLid) {
            for (x in 0 until xLid) {
                display[y][x] = true
            }
        }
    }

    fun rotateRow(display: Array<Array<Boolean>>, yRow: Int, shift: Int) {
        val row = display[yRow]
        display[yRow] = (row.takeLast(shift) + row.dropLast(shift)).toTypedArray()
    }

    fun rotateColumn(display: Array<Array<Boolean>>, xColumn: Int, shift: Int) {
        val row = (0 until columnHeight).map { display[it][xColumn] }.toTypedArray()
        val newColumn = (row.takeLast(shift) + row.dropLast(shift)).toTypedArray()
        (0 until columnHeight).forEach { display[it][xColumn] = newColumn[it] }
    }
}

val input8 = """rect 1x1
rotate row y=0 by 10
rect 1x1
rotate row y=0 by 10
rect 1x1
rotate row y=0 by 5
rect 1x1
rotate row y=0 by 3
rect 2x1
rotate row y=0 by 4
rect 1x1
rotate row y=0 by 3
rect 1x1
rotate row y=0 by 2
rect 1x1
rotate row y=0 by 3
rect 2x1
rotate row y=0 by 2
rect 1x1
rotate row y=0 by 3
rect 2x1
rotate row y=0 by 5
rotate column x=0 by 1
rect 4x1
rotate row y=1 by 12
rotate row y=0 by 10
rotate column x=0 by 1
rect 9x1
rotate column x=7 by 1
rotate row y=1 by 3
rotate row y=0 by 2
rect 1x2
rotate row y=1 by 3
rotate row y=0 by 1
rect 1x3
rotate column x=35 by 1
rotate column x=5 by 2
rotate row y=2 by 5
rotate row y=1 by 5
rotate row y=0 by 2
rect 1x3
rotate row y=2 by 8
rotate row y=1 by 10
rotate row y=0 by 5
rotate column x=5 by 1
rotate column x=0 by 1
rect 6x1
rotate row y=2 by 7
rotate row y=0 by 5
rotate column x=0 by 1
rect 4x1
rotate column x=40 by 2
rotate row y=2 by 10
rotate row y=0 by 12
rotate column x=5 by 1
rotate column x=0 by 1
rect 9x1
rotate column x=43 by 1
rotate column x=40 by 2
rotate column x=38 by 1
rotate column x=15 by 1
rotate row y=3 by 35
rotate row y=2 by 35
rotate row y=1 by 32
rotate row y=0 by 40
rotate column x=32 by 1
rotate column x=29 by 1
rotate column x=27 by 1
rotate column x=25 by 1
rotate column x=23 by 2
rotate column x=22 by 1
rotate column x=21 by 3
rotate column x=20 by 1
rotate column x=18 by 3
rotate column x=17 by 1
rotate column x=15 by 1
rotate column x=14 by 1
rotate column x=12 by 1
rotate column x=11 by 3
rotate column x=10 by 1
rotate column x=9 by 1
rotate column x=8 by 2
rotate column x=7 by 1
rotate column x=4 by 1
rotate column x=3 by 1
rotate column x=2 by 1
rotate column x=0 by 1
rect 34x1
rotate column x=44 by 1
rotate column x=24 by 1
rotate column x=19 by 1
rotate row y=1 by 8
rotate row y=0 by 10
rotate column x=8 by 1
rotate column x=7 by 1
rotate column x=6 by 1
rotate column x=5 by 2
rotate column x=3 by 1
rotate column x=2 by 1
rotate column x=1 by 1
rotate column x=0 by 1
rect 9x1
rotate row y=0 by 40
rotate column x=43 by 1
rotate row y=4 by 10
rotate row y=3 by 10
rotate row y=2 by 5
rotate row y=1 by 10
rotate row y=0 by 15
rotate column x=7 by 2
rotate column x=6 by 3
rotate column x=5 by 2
rotate column x=3 by 2
rotate column x=2 by 4
rotate column x=0 by 2
rect 9x2
rotate row y=3 by 47
rotate row y=0 by 10
rotate column x=42 by 3
rotate column x=39 by 4
rotate column x=34 by 3
rotate column x=32 by 3
rotate column x=29 by 3
rotate column x=22 by 3
rotate column x=19 by 3
rotate column x=14 by 4
rotate column x=4 by 3
rotate row y=4 by 3
rotate row y=3 by 8
rotate row y=1 by 5
rotate column x=2 by 3
rotate column x=1 by 3
rotate column x=0 by 2
rect 3x2
rotate row y=4 by 8
rotate column x=45 by 1
rotate column x=40 by 5
rotate column x=26 by 3
rotate column x=25 by 5
rotate column x=15 by 5
rotate column x=10 by 5
rotate column x=7 by 5
rotate row y=5 by 35
rotate row y=4 by 42
rotate row y=2 by 5
rotate row y=1 by 20
rotate row y=0 by 45
rotate column x=48 by 5
rotate column x=47 by 5
rotate column x=46 by 5
rotate column x=43 by 5
rotate column x=41 by 5
rotate column x=38 by 5
rotate column x=37 by 5
rotate column x=36 by 5
rotate column x=33 by 1
rotate column x=32 by 5
rotate column x=31 by 5
rotate column x=30 by 1
rotate column x=28 by 5
rotate column x=27 by 5
rotate column x=26 by 5
rotate column x=23 by 1
rotate column x=22 by 5
rotate column x=21 by 5
rotate column x=20 by 1
rotate column x=17 by 5
rotate column x=16 by 5
rotate column x=13 by 1
rotate column x=12 by 3
rotate column x=7 by 5
rotate column x=6 by 5
rotate column x=3 by 1
rotate column x=2 by 3"""

val t = PinCodeDisplay()
println("Part One")
println(t.countLid(input8))
println("Part Two")
println(t.code(input8))
