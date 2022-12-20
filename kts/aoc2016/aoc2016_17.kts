val m = java.security.MessageDigest.getInstance("MD5")!!
fun String.md5(): String {
    m.update(this.toByteArray(), 0, this.length)
    return java.math.BigInteger(1, m.digest()).toString(16).padStart(32, '0')
}

data class Cabinet(val x: Int, val y: Int, val path: String, private val key: String) {
    private enum class Direction(val dir: Char, val xD: Int, val yD: Int, val slot: Int) {
        Up('U', 0, -1, 0),
        Down('D', 0, 1, 1),
        Left('L', -1, 0, 2),
        Right('R', 1, 0, 3)
    }

    private val pathHash = (key + path).md5().substring(0..3)

    fun getNeighbors(): List<Cabinet> =
        Direction.values()
            .filter { pathHash[it.slot] in ('b'..'f') }
            .map { Cabinet(x + it.xD, y + it.yD, path + it.dir, key) }
            .filter { it.x in (0..3) && it.y in (0..3) }
}

fun findPath(input: String): List<String> {
    val start = Cabinet(0, 0, "", input)
    val toBeEvaluated = java.util.ArrayDeque<Cabinet>().apply { add(start) }
    var path: List<String> = listOf()
    while (toBeEvaluated.isNotEmpty()) {
        val cabinet = toBeEvaluated.poll()

        if (cabinet.x == Pair(3, 3).first && cabinet.y == Pair(3, 3).second) {
            path += cabinet.path
            continue
        }

        toBeEvaluated.addAll(cabinet.getNeighbors())
    }

    return path
}

println("Part One")
println(findPath("udskfozm").first())
println("Part Two")
println(findPath("udskfozm").last().length)
