import kotlin.math.min

@Suppress("ControlFlowWithEmptyBody")
fun main() {
    fun simulate(matrix: List<StringBuilder>, dy: Int, dx: Int): Boolean {
        var moved = false

        for (y in 0..matrix.lastIndex) {
            for (x in matrix[y].indices) {
                if (matrix[y][x] == 'O' &&
                    matrix.elementAtOrNull(y + dy)?.elementAtOrNull(x + dx) == '.'
                ) {
                    matrix[y + dy][x + dx] = 'O'
                    matrix[y][x] = '.'
                    moved = true
                }
            }
        }

        return moved
    }

    fun score(matrix: List<StringBuilder>): Int {
        val max = matrix.size
        val sum = matrix.mapIndexed { y, row -> row.count { it == 'O' } * (max - y) }.sum()
        return sum
    }

    fun part1(input: List<String>): Int {
        val matrix = input.map { StringBuilder(it) }

        while (simulate(matrix, -1, 0)) {
        }

        return score(matrix)
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { StringBuilder(it) }

        val seen = mutableListOf<String>()

        var i = 1
        var period = 0
        while (i <= 1000000000) {
            while (simulate(matrix, -1, 0)) {
            }
            while (simulate(matrix, 0, -1)) {
            }
            while (simulate(matrix, 1, 0)) {
            }
            while (simulate(matrix, 0, 1)) {
            }

            val stringified = matrix.joinToString("\n") { it.toString() }
            val index = seen.indexOf(stringified)
            if (index < 0) {
                seen.add(stringified)
                i++
            } else {
                period = maxOf(period, seen.size - index)
                if ((1000000000 - i) % period == 0) break // wat?!
                i++
            }
        }

        return score(matrix)
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136)
    check(part2(testInput) == 64)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
