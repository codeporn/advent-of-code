import io.wende.aoc.Runner
import io.wende.aoc.common.Task
import io.wende.aoc.common.Util
import java.io.File
import java.io.Serializable
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

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
    NORTH, EAST, SOUTH, WEST;

    companion object {
        var dirMap = mapOf("R" to EAST, "L" to WEST, "U" to NORTH, "D" to SOUTH,
            ">" to EAST, "<" to WEST, "^" to NORTH, "v" to SOUTH)
        fun from(type: String?): Direction = entries.find { it.name == type } ?: dirMap[type] ?: NORTH
    }
}

data class Point(var x: Long, var y: Long) : Serializable {

    fun walk(direction: Direction): Point {
        when (direction) {
            Direction.NORTH -> this.y -= 1
            Direction.EAST -> this.x += 1
            Direction.SOUTH -> this.y += 1
            Direction.WEST -> this.x -= 1
        }
        return this
    }

    fun snap(): Point {
        return Point(this.x, this.y)
    }

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

fun shoelace(points: List<Point>): Long = (0 until points.size - 1).sumOf {
    (points[it].x * points[(it + 1) ].y) - (points[it].y * points[(it + 1)].x)
}.absoluteValue / 2

fun innerGraphArea(points: List<Point>): Long = (shoelace(points) - points.size/2 + 1)

fun outerGraphArea(points: List<Point>): Long = (shoelace(points) - points.size/2 + 1) + points.size

@OptIn(ExperimentalStdlibApi::class)
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}

val <T> List<T>.head: T
    get() = first()

val <T> List<T>.tail: List<T>
    get() = drop(1)

// Extension function

fun <T> List<T>.permutations(): List<List<T>> {
    if (isEmpty()) return emptyList()
    if (size == 1) return listOf(this)

    return tail.permutations().fold(mutableListOf()) { acc, perm ->
        (0..perm.size).mapTo(acc) { i ->
            perm.subList(0, i) + head + perm.subList(i, perm.size)
        }
    }
}

fun <T> List<T>.zipWithAllUnique(): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()
    val visited = mutableSetOf<Pair<T, T>>()

    for (i in this.indices) {
        for (j in i + 1 until this.size) {
            val pair = Pair(this[i], this[j])
            if (pair !in visited) {
                result.add(pair)
                visited.add(pair)
                visited.add(pair.copy(first = pair.second, second = pair.first)) // Add reverse pair as well
            }
        }
    }
    return result
}