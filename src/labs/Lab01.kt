import java.util.Locale

open class A<T> {
    open fun print(x: T) {
        println(x)
    }
}

class B : A<String>() {
    override fun print(x: String) {
        println(x.uppercase(Locale.getDefault()))
    }
}


fun main() {
    val b = B()

    @Suppress("UNCHECKED_CAST")
    val a: A<Any> = b as A<Any>

    a.print("hello")
}
