fun getNthKeyIndex(nMax: Int, salt: String, hashFun: (String) -> String): Map<Int, String> {
    val hashList = mutableMapOf<Int, String>()
    var i = 0
    var count = 0
    while (true) {
        i++
        val hash = hashFun((salt + i.toString()))
        val chars = getRepeatedChar(3, hash)
        // for (char in chars) {
        if (chars.isNotEmpty()) {
            val char = chars.first()
            if (containsInNext1000Hashes(i, salt, char, hashFun)) {
                count++
                hashList.put(i, hash)
                if (count == nMax) {
                    return hashList
                }
                // break
            }
        }
    }
}

fun containsInNext1000Hashes(from: Int, salt: String, char: Char, hashFun: (String) -> String): Boolean {
    var i = from
    while (i < from + 1000) {
        i++
        val hash = hashFun((salt + i.toString()))
        if (char.toString().repeat(5) in hash) {
            return true
        }
    }

    return false
}

fun getRepeatedChar(needRepeats: Int, str: String): Set<Char> {
    val triceChars = mutableSetOf<Char>()
    var repeats = 0
    var prevChar: Char? = null
    for (char in str) {
        if (prevChar == char) {
            repeats++
        } else {
            repeats = 0
        }

        if (repeats == needRepeats - 1) {
            triceChars.add(char)
        }

        prevChar = char
    }

    return triceChars
}

fun hash(str: String): String = str.md5()

val hashCache = mutableMapOf<String, String>()
fun hashCached(str: String): String {
    if (!hashCache.containsKey(str)) {
        hashCache[str] = hash(str)
    }

    return hashCache[str]!!
}

fun hash2016(str: String): String = (1..2016).fold(str.md5()) { carry, next -> carry.md5() }

val hash2016Cache = mutableMapOf<String, String>()
fun hash2016Cached(str: String): String {
    if (!hash2016Cache.containsKey(str)) {
        hash2016Cache[str] = hash2016(str)
    }

    return hash2016Cache[str]!!
}

val m = java.security.MessageDigest.getInstance("MD5")!!
fun String.md5(): String {
    m.update(this.toByteArray(), 0, this.length)

    return java.math.BigInteger(1, m.digest()).toString(16).padStart(32, '0')
}

println("Part One")
println(getNthKeyIndex(64, "zpqevtbw", { v -> hashCached(v) }).toList().last())
println("Part Two")
println(getNthKeyIndex(64, "zpqevtbw", { v -> hash2016Cached(v) }).toList().last())
