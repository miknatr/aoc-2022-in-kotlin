import java.lang.Math.abs

val input = 361527

fun p1(target: Int): Int {
    var x = 0
    var y = 0
    var dx = 0
    var dy = -1
    var dxOrig: Int
    var step = 0

    while (true) {
        step++

        if (target == step) {
            return abs(x) + abs(y)
        }

        if (x == y || (x < 0 && x == -y) || (x > 0 && x == 1 - y)) {
            dxOrig = dx
            dx = -dy
            dy = dxOrig
        }

        x += dx
        y += dy
    }
}

println(p1(input))

// https://oeis.org/A141481
// https://oeis.org/A141481/b141481.txt
println(363010)
