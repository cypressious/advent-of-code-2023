import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>): Int {
        val durations = input[0].split(" +".toRegex()).mapNotNull { it.toIntOrNull() }
        val records = input[1].split(" +".toRegex()).mapNotNull { it.toIntOrNull() }

        return durations.zip(records).map { (duration, record) ->
            (1..<duration).count {
                it * (duration - it) > record
            }
        }.fold(1, Int::times)
    }


    fun part2(input: List<String>): Int {
        val d = input[0].filter { it.isDigit() }.toDouble()
        val r = input[1].filter { it.isDigit() }.toDouble()

        val x1 = ceil(0.5 * (d - sqrt((d * d - 4 * r)))).toInt()
        val x2 = floor(0.5 * (d + sqrt((d * d - 4 * r)))).toInt()

        return x2 - x1 +1
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
