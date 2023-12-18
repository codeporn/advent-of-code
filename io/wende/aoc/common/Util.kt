import io.wende.aoc.Runner
import io.wende.aoc.common.Task
import io.wende.aoc.common.Util
import java.io.File
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun String.isNumeric(): Boolean = this.toDoubleOrNull() != null

fun dayAndYear(): Pair<Int, Int> = LocalDateTime.now()
    .format(DateTimeFormatter.ofPattern("d,yy"))
    .split(",").let{ it[0].toInt() to it[1].toInt() }

fun findTodaysTaskInstance(test: Boolean = false): Task? = findTaskInstance(dayAndYear().first, dayAndYear().second, test)

fun findTaskInstance(day: Int, year: Int, test: Boolean = false) : Task? = findTaskClasses("${Runner.javaClass.packageName}.${numbers[if(year > 99) year % 100 else year]}")
    .find { it.simpleName.equals(numbers[day], true) }?.getDeclaredConstructor(Boolean::class.java)?.newInstance(test) as Task?

fun findTaskClasses(packageName: String): List<Class<*>> {
    var name = packageName
    if (!name.startsWith("/")) {
        name = "/$name"
    }
    name = name.replace('.', '/')
    val directory = File(Util::class.java.getResource(name).getFile())

    if (directory.exists()) {
        // Get the list of the files contained in the package
        return directory.walk()
            .filter { f -> f.isFile() && f.name.contains('$') == false && f.name.endsWith(".class") }
            .mapNotNull {
                Class.forName(packageName +
                        it.canonicalPath.removePrefix(directory.canonicalPath)
                            .dropLast(6) // remove .class
                            .replace("[\\/\\\\]".toRegex(), "."))
            }.filter {
                it.superclass == Task::class.java
            }.toList()
    }
    return listOf()
}

val numbers: Map<Int, String> = mapOf(
    1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five", 6 to "six", 7 to "seven", 8 to "eight",
    9 to "nine", 10 to "ten", 11 to "eleven", 12 to "twelve", 13 to "thirteen", 14 to "fourteen", 15 to "fifteen",
    16 to "sixteen", 17 to "seventeen", 18 to "eighteen", 19 to "nineteen", 20 to "twenty", 21 to "twentyone",
    22 to "twentytwo", 23 to "twentythree", 24 to "twentyfour", 25 to "twentyfive" )

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

data class Point(val x: Long, val y: Long) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}