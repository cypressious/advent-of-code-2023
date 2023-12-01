fun main() {
    val digitWords =
        listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val digits = it.mapNotNull(Char::digitToIntOrNull)
            digits.first() * 10 + digits.last()
        }
    }


    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val digits = line.indices
                .mapNotNull { i ->
                    line[i].digitToIntOrNull()
                        ?: digitWords.indexOfFirst(line.substring(i)::startsWith)
                            .takeUnless { it < 0 }
                }
            digits.first() * 10 + digits.last()
        }
    }

    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
