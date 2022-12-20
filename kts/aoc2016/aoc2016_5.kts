class PasswordGenerator {
    fun genPass(input: String): String {
        var password = ""
        var i = 0

        while (password.length < 8) {
            val hash = (input + i.toString()).md5()
            if (hash.subSequence(0, 5).toString() == "00000") {
                password += hash[5].toString()
            }

            i++
        }

        return password
    }

    fun genPass2(input: String): String {
        val password = mutableListOf<Char>('-', '-', '-', '-', '-', '-', '-', '-')
        var i = 0

        while (password.contains('-')) {
            val hash = (input + i.toString()).md5()
            if (hash.subSequence(0, 5).toString() == "00000") {
                try {
                    val pos = hash[5].toString().toInt()

                    if (pos < 8 && password[pos] == '-') {
                        password.set(pos, hash[6])
                    }
                } catch (e: NumberFormatException) {
                }
            }

            i++
        }

        return password.fold("") { s, c -> s + c.toString() }
    }
}

val m = java.security.MessageDigest.getInstance("MD5")!!
fun String.md5(): String {
    m.update(this.toByteArray(), 0, this.length)

    return java.math.BigInteger(1, m.digest()).toString(16).padStart(32, '0')
}

val input5 = "ugkcyxxp"

val t = PasswordGenerator()
println("Part One")
println(t.genPass(input5))
println("Part Two")
println(t.genPass2(input5))
