fun main() {
    data class Point(val x: Int, val y: Int)

    val UP = 0
    val RIGHT = 1
    val DOWN = 2
    val LEFT = 3

    val directions = arrayOf(
        Point(0, -1), // 0 = UP
        Point(1, 0), // 1 = RIGHT
        Point(0, 1), // 2 = DOWN
        Point(-1, 0) // 3 = LEFT
    )

    fun solve(input: List<String>, startingPoint: Point, direction: Int): Int {
        val energizedTiles = mutableMapOf<Point, BooleanArray>()

        fun follow(point: Point, dir: Int) {
            var currentPosition = point
            var currentDirection = dir

            while (currentPosition.y in input.indices && currentPosition.x in input[0].indices) {
                val energizedDirections =
                    energizedTiles.getOrPut(currentPosition) { BooleanArray(4) }
                if (energizedDirections[currentDirection]) {
                    return
                } else {
                    energizedDirections[currentDirection] = true
                }

                val currentTile = input[currentPosition.y][currentPosition.x]

                when (currentTile) {
                    '/' -> currentDirection = when (currentDirection) {
                        UP -> RIGHT
                        RIGHT -> UP
                        DOWN -> LEFT
                        else -> DOWN
                    }

                    '\\' -> currentDirection = when (currentDirection) {
                        UP -> LEFT
                        LEFT -> UP
                        DOWN -> RIGHT
                        else -> DOWN
                    }

                    '|' -> if (currentDirection % 2 == 1) {
                        follow(Point(currentPosition.x, currentPosition.y - 1), UP)
                        follow(Point(currentPosition.x, currentPosition.y + 1), DOWN)
                        return
                    }

                    '-' -> if (currentDirection % 2 == 0) {
                        follow(Point(currentPosition.x - 1, currentPosition.y), LEFT)
                        follow(Point(currentPosition.x + 1, currentPosition.y), RIGHT)
                        return
                    }
                }

                val diff = directions[currentDirection]
                currentPosition =
                    Point(currentPosition.x + diff.x, currentPosition.y + diff.y)
            }
        }

        follow(startingPoint, direction)

        return energizedTiles.count()
    }

    fun part1(input: List<String>): Int {
        return solve(input, Point(0, 0), RIGHT)
    }

    fun part2(input: List<String>): Int {
        var max = 0

        for (y in input.indices) {
            max = maxOf(max, solve(input, Point(0, y), RIGHT))
            max = maxOf(max, solve(input, Point(input[y].lastIndex, y), LEFT))
        }
        for (x in input[0].indices) {
            max = maxOf(max, solve(input, Point(x, 0), DOWN))
            max = maxOf(max, solve(input, Point(x, input.lastIndex), UP))
        }

        return max
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46)
    check(part2(testInput) == 51)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
