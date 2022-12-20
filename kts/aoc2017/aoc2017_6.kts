val input = """10	3	15	10	5	15	5	15	9	2	5	8	5	2	3	6"""

val banks = input.split("\t").map { it.toInt() }.toMutableList()
val states = mutableMapOf<String, Int>()

var i = 0

while (!states.containsKey(banks.toString())) {
    states[banks.toString()] = i

    val max = banks.max()!!
    val maxI = banks.indexOf(max)

    for (i in 1..max) {
        val changeI = (maxI + i) % banks.size
        banks[changeI] = banks[changeI] + 1
    }

    banks[maxI] = banks[maxI] - max

    i++
}

println(i)
println(i - states[banks.toString()]!!)
