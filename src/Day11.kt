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

    fun part1(input: List<String>): Int {
        val matrix = input.mapTo(mutableListOf(), ::StringBuilder)

        var i = 0
        while (i in matrix.indices) {
            if (matrix[i].all { it == '.' }) {
                matrix.add(i, StringBuilder(matrix[i]))
                i++
            }
            i++
        }

        var j = 0
        while (j < matrix[0].lastIndex) {
            if (matrix.indices.all { y -> matrix[y][j] == '.' }) {
                for (m in matrix) {
                    m.insert(j, '.')
                }
                j++
            }
            j++
        }

        val galaxies = galaxies(matrix)

        var sum = 0

        for (i in galaxies.indices) {
            for (j in galaxies.indices.drop(i + 1)) {
                val a = galaxies[i]
                val b = galaxies[j]
                sum += abs(a.x - b.x) + abs(a.y - b.y)
            }
        }

        return sum
    }

    fun part2(input: List<String>): Long {
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

                sum += abs(a.x - b.x) + abs(a.y - b.y) + (eX + eY) * (1000000 - 1)
            }
        }

        return sum
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374)
    // check(part2(testInput) == 0)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
