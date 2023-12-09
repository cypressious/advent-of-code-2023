fun main() {
    class Sequence(values: List<Int>) {
        val derivations = mutableListOf(values)

        init {
            while (!derivations.last().all { it == 0 }) {
                derivations.add(
                    derivations.last().windowed(2, partialWindows = false) { (a, b) -> b - a }
                )
            }
        }
    }

    fun List<String>.toSequences() =
        map { line -> Sequence(line.split(" ").map { it.toInt() }) }

    fun part1(input: List<String>): Int {
        val sequences = input.toSequences()

        return sequences.sumOf { s ->
            var nextValue = 0

            for (values in s.derivations.asReversed().drop(1)) {
                nextValue += values.last()
            }

            nextValue
        }
    }

    fun part2(input: List<String>): Int {
        val sequences = input.toSequences()

        return sequences.sumOf { s ->
            var nextValue = 0

            for (values in s.derivations.asReversed().drop(1)) {
                nextValue = values.first() - nextValue
            }

            nextValue
        }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
