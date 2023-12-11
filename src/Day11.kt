import kotlin.math.abs

fun main() {
    data class Coordinate(val x: Int, val y: Int)

    fun galaxies(matrix: MutableList<StringBuilder>): List<Coordinate> = buildList {
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x] == '#') this += Coordinate(x, y)
            }
        }
    }

    fun doPart(input: List<String>, expansionFactor: Int): Long {
        val matrix = input.mapTo(mutableListOf(), ::StringBuilder)
        val expansionsX = mutableListOf<Int>()
        val expansionsY = mutableListOf<Int>()

        for (y in matrix.indices) {
            if (matrix[y].all { it == '.' }) {
                expansionsY += y
            }
        }

        for (x in matrix[0].indices) {
            if (matrix.indices.all { y -> matrix[y][x] == '.' }) {
                expansionsX += x
            }
        }

        val galaxies = galaxies(matrix)

        var sum = 0L

        for (i in galaxies.indices) {
            for (j in galaxies.indices.drop(i + 1)) {
                val a = galaxies[i]
                val b = galaxies[j]

                val eX = (minOf(a.x, b.x)..maxOf(a.x, b.x)).count { it in expansionsX }
                val eY = (minOf(a.y, b.y)..maxOf(a.y, b.y)).count { it in expansionsY }


                sum += abs(a.x - b.x) + abs(a.y - b.y) + (eX + eY) * (expansionFactor - 1)
            }
        }

        return sum
    }

    fun part1(input: List<String>): Long {
        return doPart(input, 2)
    }

    fun part2(input: List<String>): Long {
        return doPart(input, 1000000)
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374L)
    // check(part2(testInput) == 0)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
