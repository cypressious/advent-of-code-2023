import kotlin.math.abs

fun main() {
    data class Coordinate(val x: Int, val y: Int)

    fun areAdjacent(c1: Coordinate, c2: Coordinate): Boolean {
        val dx = abs(c1.x - c2.x)
        val dy = abs(c1.y - c2.y)
        return dx <= 1 && dy <= 1
    }

    fun getSymbolCoordinates(input: List<String>): List<Coordinate> {
        val coordinateList = mutableListOf<Coordinate>()
        input.forEachIndexed { y, str ->
            str.forEachIndexed { x, char ->
                if (!char.isDigit() && char != '.') {
                    coordinateList.add(Coordinate(x, y))
                }
            }
        }
        return coordinateList
    }

    fun isAdjacentToSymbol(coordinate: Coordinate, symbolCoordinates: List<Coordinate>): Boolean {
        return symbolCoordinates.any { areAdjacent(coordinate, it) }
    }

    fun part1(input: List<String>): Int {
        val symbolCoordinates = getSymbolCoordinates(input)
        val filteredNumbers = mutableListOf<Int>()

        for (y in input.indices) {
            val currentNumber = StringBuilder()
            var isAdjacentToSymbol = false

            for (x in input[y].indices) {
                val c = input[y][x]

                if (c.isDigit()) {
                    currentNumber.append(c)
                    isAdjacentToSymbol = isAdjacentToSymbol || isAdjacentToSymbol(Coordinate(x,y), symbolCoordinates)
                } else {
                    if (isAdjacentToSymbol) {
                        filteredNumbers += currentNumber.toString().toInt()
                    }
                    currentNumber.clear()
                    isAdjacentToSymbol = false
                }
            }

            if (isAdjacentToSymbol) {
                filteredNumbers += currentNumber.toString().toInt()
            }
        }

        return filteredNumbers.sum()
    }

    fun getStarCoordinates(input: List<String>): List<Coordinate> {
        val coordinateList = mutableListOf<Coordinate>()
        input.forEachIndexed { y, str ->
            str.forEachIndexed { x, char ->
                if (char == '*') {
                    coordinateList.add(Coordinate(x, y))
                }
            }
        }
        return coordinateList
    }

    fun part2(input: List<String>): Int {
        val starCoordinates = getStarCoordinates(input)
        val starNumbers = mutableMapOf<Coordinate, MutableList<Int>>()

        for (y in input.indices) {
            val currentNumber = StringBuilder()
            val currentStars = mutableSetOf<Coordinate>()

            for (x in input[y].indices) {
                val c = input[y][x]

                if (c.isDigit()) {
                    currentNumber.append(c)
                    currentStars.addAll(starCoordinates.filter { areAdjacent(Coordinate(x, y), it) })
                } else {
                    for (star in currentStars) {
                        starNumbers.getOrPut(star, ::mutableListOf).add(currentNumber.toString().toInt())
                    }
                    currentNumber.clear()
                    currentStars.clear()
                }
            }

            for (star in currentStars) {
                starNumbers.getOrPut(star, ::mutableListOf).add(currentNumber.toString().toInt())
            }
        }

        return starNumbers.values.filter { it.size == 2 }.sumOf { it.reduce(Int::times) }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
