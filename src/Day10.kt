import kotlin.math.ceil

fun main() {
    val pipes = mapOf(
        '|' to "NS",
        '-' to "EW",
        'L' to "NE",
        'J' to "NW",
        '7' to "SW",
        'F' to "SE",
    )

    val opposites = mapOf(
        'N' to 'S',
        'E' to 'W',
        'S' to 'N',
        'W' to 'E',
    )

    data class Coordinate(val x: Int, val y: Int) {
        operator fun plus(char: Char) = when (char) {
            'N' -> Coordinate(x, y - 1)
            'S' -> Coordinate(x, y + 1)
            'E' -> Coordinate(x + 1, y)
            else -> Coordinate(x - 1, y)
        }
    }

    operator fun List<String>.get(c: Coordinate) = elementAtOrNull(c.y)?.elementAtOrNull(c.x)

    fun findStartingPosition(matrix: List<String>): Coordinate {
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x] == 'S') {
                    return Coordinate(x, y)
                }
            }
        }
        throw IllegalArgumentException("No 'S' character found in the matrix")
    }

    fun List<String>.forEachSegment(f: (Coordinate, Char) -> Unit) {
        var pos = findStartingPosition(this)
        var dir = opposites.keys.first { d ->
            this[pos + d].let { p ->
                p != null && pipes.getOrDefault(p, "").contains(opposites.getValue(d))
            }
        }

        while (true) {
            pos += dir
            f(pos, dir)
            if (this[pos] == 'S') break
            val oppositeDir = opposites.getValue(dir)
            val nextDir = pipes.getValue(this[pos]!!).first { it != oppositeDir }


            dir = nextDir
        }
    }

    fun part1(input: List<String>): Int {
        var length = 0

        input.forEachSegment { _, _ -> length++ }

        return ceil(length / 2.0).toInt()
    }

    val inside = mapOf(
        "|N" to "E",
        "|S" to "W",
        "-E" to "S",
        "-W" to "N",
        "LS" to "WS",
        "JE" to "ES",
        "7N" to "NE",
        "FW" to "NW",
    )

    fun part2(input: List<String>): Int {
        val path = mutableMapOf<Coordinate, String>()

        input.forEachSegment { c, d ->
            path[c] = inside.getOrDefault("" + input[c]!! + d, "")
        }

        val minX = path.keys.minOf { it.x } + 1
        val maxX = path.keys.maxOf { it.x } - 1
        val minY = path.keys.minOf { it.y } + 1
        val maxY = path.keys.maxOf { it.y } - 1

        var count = 0

        for (y in minY..maxY) {
            loop@
            for (x in minX..maxX) {
                var coordinate = Coordinate(x, y)
                if (coordinate in path) continue

                while (coordinate.y > 0 && coordinate !in path) {
                    coordinate += 'N'
                }

                if ('S' in path.getOrDefault(coordinate, "")) {
                    count++
                }
            }
        }

        return count
    }

    check(part1(readInput("Day10_test")) == 8)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
