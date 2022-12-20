class Transport {
    var curX = 0
    var curY = 0
    var curDirection = 0
    val directions = listOf("u", "r", "d", "l")
    val directionUpdates = mapOf("L" to -1, "R" to 1)
    val visited = mutableMapOf<XY<Int>, Int>()
    val twice = mutableListOf<XY<Int>>()

    fun moves(commands: String) {
        visit(0, 0)

        val moves = commands.split(", ")
        for (move in moves) {
            move(move)
        }
    }

    fun move(command: String) {
        val turn = command.substring(0, 1)
        val steps = command.substring(1)

        updateDirection(turn)
        updateCoords(steps)
    }

    fun updateDirection(turn: String) {
        curDirection += directionUpdates[turn]!!
        curDirection += 4
        curDirection %= 4
    }

    fun updateCoords(steps: String) {
        val num = steps.toInt()
        when (directions[curDirection]) {
            "u" -> {
                for (coord in curY + 1..curY + num) {
                    visit(curX, coord)
                }
                curY += num
            }
            "d" -> {
                for (coord in curY - 1 downTo curY - num) {
                    visit(curX, coord)
                }
                curY -= num
            }
            "r" -> {
                for (coord in curX + 1..curX + num) {
                    visit(coord, curY)
                }
                curX += num
            }
            "l" -> {
                for (coord in curX - 1 downTo curX - num) {
                    visit(coord, curY)
                }
                curX -= num
            }
        }
    }

    fun visit(vx: Int, vy: Int) {
        val pair = XY(vx, vy)

        if (!visited.containsKey(pair)) {
            visited[pair] = 0
        }

        visited[pair] = visited[pair]!! + 1

        if (visited[pair]!! == 2) {
            twice.add(pair)
        }
    }
}

fun Int.abs(): Int = if (this > 0) this else -this

data class XY<T>(var x: T, var y: T) {
    operator fun get(index: Int) = if (index == 0) x else y
}

val input = "L5, R1, R4, L5, L4, R3, R1, L1, R4, R5, L1, L3, R4, L2, L4, R2, L4, L1, R3, R1, R1, L1, R1, L5, R5, R2, L5, R2, R1, L2, L4, L4, R191, R2, R5, R1, L1, L2, R5, L2, L3, R4, L1, L1, R1, R50, L1, R1, R76, R5, R4, R2, L5, L3, L5, R2, R1, L1, R2, L3, R4, R2, L1, L1, R4, L1, L1, R185, R1, L5, L4, L5, L3, R2, R3, R1, L5, R1, L3, L2, L2, R5, L1, L1, L3, R1, R4, L2, L1, L1, L3, L4, R5, L2, R3, R5, R1, L4, R5, L3, R3, R3, R1, R1, R5, R2, L2, R5, L5, L4, R4, R3, R5, R1, L3, R1, L2, L2, R3, R4, L1, R4, L1, R4, R3, L1, L4, L1, L5, L2, R2, L1, R1, L5, L3, R4, L1, R5, L5, L5, L1, L3, R1, R5, L2, L4, L5, L1, L1, L2, R5, R5, L4, R3, L2, L1, L3, L4, L5, L5, L2, R4, R3, L5, R4, R2, R1, L5"

val t = Transport()
t.moves(input)
println("Part One")
println(t.curX.abs() + t.curY.abs())
println("Part Two")
println(t.twice[0].x.abs() + t.twice[0].y.abs())
