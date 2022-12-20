import java.io.File

class IpAnalyzer {
    fun countTlsIps(input: String): String {
        return input.split("\n").count { supportTls(it) }.toString()
    }

    private fun supportTls(input: String): Boolean {
        var inBrackets = false
        var abbaInBrackets = false
        var abbaOutBrackets = false

        for (i in 0 until input.length - 3) {
            if (input[i] == '[' && !inBrackets) {
                inBrackets = true
                continue
            }
            if (input[i] == ']' && inBrackets) {
                inBrackets = false
                continue
            }

            val abba = (input[i] == input[i + 3] && input[i + 1] == input[i + 2] && input[i] != input[i + 1])

            if (abba && inBrackets) {
                abbaInBrackets = true
            }
            if (abba && !inBrackets) {
                abbaOutBrackets = true
            }
        }

        return abbaOutBrackets && !abbaInBrackets
    }

    fun countSslIps(input: String): String {
        return input.split("\n").count { supportSsl(it) }.toString()
    }

    private fun supportSsl(input: String): Boolean {
        var inBrackets = false
        val abas = mutableSetOf<String>()
        val babs = mutableSetOf<String>()

        for (i in 0 until input.length - 2) {
            if (input[i] == '[' && !inBrackets) {
                inBrackets = true
                continue
            }
            if (input[i] == ']' && inBrackets) {
                inBrackets = false
                continue
            }

            val aba = (input[i] == input[i + 2] && input[i] != input[i + 1] && input[i] != '[' && input[i] != ']')
            if (aba) {
                if (!inBrackets) {
                    abas.add(input[i].toString() + input[i + 1].toString())
                } else {
                    babs.add(input[i + 1].toString() + input[i].toString())
                }
            }
        }

        return abas.any { babs.contains(it) }
    }
}

val input7 = File("aoc2016_7.txt").readText().trim()

val t = IpAnalyzer()
println("Part One")
println(t.countTlsIps(input7))
println("Part Two")
println(t.countSslIps(input7))
