data class Coord(var x: Int, var y: Int) {
    operator fun get(index: Int) = if (index == 0) x else y
    fun isNegative() = x < 0 || y < 0
    fun move(move: Coord) = Coord(x + move.x, y + move.y)
}

fun isWallCreator(favNum: Int) = { (x, y): Coord -> countSetBits(genNumber(favNum, x, y)) % 2 == 1 }
fun genNumber(favNum: Int, x: Int, y: Int) = favNum + x * x + 3 * x + 2 * x * y + y + y * y
fun countSetBits(number: Int): Int {
    var curNumber = number
    var count = 0
    while (curNumber > 0) {
        count += (curNumber and 1).toInt()
        curNumber = curNumber shr 1
    }
    return count
}

fun moveThrough(endPoint: Coord, isWall: (Coord) -> Boolean): Int {
    val visited = mutableSetOf(Coord(1, 1))

    var stepPoints = mutableSetOf(Coord(1, 1))
    var step = 0

    while (endPoint !in stepPoints) {
        stepPoints = nextStepMovies(stepPoints, isWall, visited)
        step++
    }

    return step
}

fun uniqueVisited(maxStep: Int, isWall: (Coord) -> Boolean): Int {
    val visited = mutableSetOf(Coord(1, 1))

    var stepPoints = mutableSetOf(Coord(1, 1))
    var step = 0

    while (step < maxStep) {
        stepPoints = nextStepMovies(stepPoints, isWall, visited)
        step++
    }

    return visited.size
}

val possibleMoves = listOf(
        Coord(0, 1),
        Coord(1, 0),
        Coord(0, -1),
        Coord(-1, 0)
)

fun nextStepMovies(stepPoints: MutableSet<Coord>, isWall: (Coord) -> Boolean, visited: MutableSet<Coord>): MutableSet<Coord> {
    val newStepPoints = mutableSetOf<Coord>()

    for (stepPoint in stepPoints) {
        for (move in possibleMoves) {
            val tryPoint = stepPoint.move(move)

            if (isWall(tryPoint) || tryPoint.isNegative() || tryPoint in visited) {
                continue
            }

            visited.add(tryPoint.copy())
            newStepPoints.add(tryPoint.copy())
        }
    }

    return newStepPoints
}

println("Part One")
println(moveThrough(Coord(31, 39), isWallCreator(1362)))
println("Part Two")
println(uniqueVisited(50, isWallCreator(1362)))
