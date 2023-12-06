import kotlin.math.pow

fun main() {
    fun parseLine(line: String): Pair<Set<Int>, List<Int>> {
        val parts = line.split(':', '|').map { it.trim() }
        val beforeDash = parts[1].split(" +".toRegex()).map { it.toInt() }
        val afterDash = parts[2].split(" +".toRegex()).map { it.toInt() }
        return beforeDash.toSet() to afterDash
    }

    fun winningCount(line: String): Int {
        val (before, after) = parseLine(line)
        val winningCount = after.count { it in before }
        return winningCount
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val winningCount = winningCount(line)
            2.0.pow((winningCount - 1.0)).toInt()
        }
    }


    fun part2(input: List<String>): Int {
        val copies = IntArray(input.size) { 1 }

        for ((i, line) in input.withIndex()) {
            val winningCount = winningCount(line)

            for (j in i + 1..i + winningCount) {
                copies[j] += copies[i]
            }
        }

        return copies.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
