import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface PrinterInterface {
    fun print()
}

class Printer(private val x: Int) : PrinterInterface {
    override fun print() {
        println(x)
    }
}

interface ScannerInterface {
    fun scan()

}

class Scanner(private val y: Int) : ScannerInterface {
    override fun scan() {
        println(y)
    }
}
class RandomStringProperty() : ReadOnlyProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return Math.random().toString()
    }
}

class PrinterAndScanner(
    printer: PrinterInterface,
    scanner: ScannerInterface
) :
    PrinterInterface by printer,
    ScannerInterface by scanner
{
    var report: String by Delegates.observable("Init report") { prop, old, new ->
        println("$old -> $new")
    }

    val random: String by RandomStringProperty()
}

fun main() {
    val printer = Printer(10)
    val scanner = Scanner(20)
    val mfu = PrinterAndScanner(printer, scanner)

    mfu.print()
    mfu.scan()

    mfu.report = "New report"

    println(mfu.random)
    println(mfu.random)
}
