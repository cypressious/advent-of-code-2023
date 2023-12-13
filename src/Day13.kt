fun main() {
    fun splitInput(input: List<String>): List<List<String>> {
        val chunks = mutableListOf<List<String>>()
        var chunk = mutableListOf<String>()
        for (line in input) {
            if (line.isBlank()) {
                chunks.add(chunk)
                chunk = mutableListOf()
            } else {
                chunk.add(line)
            }
        }
        if (chunk.isNotEmpty()) chunks.add(chunk)
        return chunks
    }

    fun transpose(matrix: List<String>): List<String> {
        val transposed = mutableListOf<String>()

        for (x in matrix[0].indices) {
            transposed += matrix.joinToString("") { it[x].toString() }
        }


        return transposed
    }

    fun getReflectionValue(matrix: List<String>, ignore: Int?): Int? {
        val candidates = (0..<matrix[0].length - 1).toMutableSet()

        for (row in matrix) {
            val stacks = Array<MutableList<Char>?>(row.length) { mutableListOf() }
            for ((i, c) in row.withIndex()) {
                for (j in 0..<i) {
                    val stack = stacks[j]
                    if (stack?.lastOrNull() == c) {
                        stack.removeLast()
                    } else if (stack?.isNotEmpty() == true) {
                        stacks[j] = null
                    }
                }
                for (j in i..row.lastIndex) {
                    stacks[j]!!.add(c)
                }
            }

            candidates.removeIf { stacks[it] == null }
        }

        return candidates.firstOrNull { it != ignore?.minus(1) }?.plus(1)
    }

    fun solve(it: List<String>, ignore: Int? = null): Int? {
        val columns = getReflectionValue(it, ignore)
        val rows = getReflectionValue(transpose(it), ignore?.div(100))

        return columns ?: rows?.times(100)
    }

    fun part1(input: List<String>): Int {
        return splitInput(input).sumOf { solve(it)!! }
    }

    fun solvePart2(matrix: List<String>): Int {
        val original = solve(matrix)

        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                val newMatrix = matrix
                    .mapIndexed { yy, row ->
                        if (yy == y) {
                            row.replaceRange(x, x + 1, if (row[x] == '.') "#" else ".")
                        } else {
                            row
                        }
                    }
                solve(newMatrix, original)?.let { return it }
            }
        }
        error("")
    }

    fun part2(input: List<String>): Int {
        val splitInput = splitInput(input)
        return splitInput
            .parallelStream()
            .mapToInt { matrix -> solvePart2(matrix) }
            .sum()
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405)
    check(part2(testInput) == 400)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
