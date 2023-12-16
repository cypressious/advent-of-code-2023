fun main() {

    fun hash(input: String): Int {
        var value = 0
        input.forEach { character ->
            value += character.code
            value *= 17
            value %= 256
        }
        return value
    }

    fun part1(input: List<String>): Int {
        return input[0].split(",").sumOf(::hash)
    }

    data class Lens(val label: String, var value: Int)

    fun part2(input: List<String>): Int {
        val map = mutableMapOf<Int, MutableList<Lens>>()

        for (step in input[0].split(",")) {
            if ('=' in step) {
                val (label, value) = step.split('=')
                val lenses = map.getOrPut(hash(label), ::mutableListOf)
                val existing = lenses.firstOrNull { it.label == label }
                if (existing != null) {
                    existing.value = value.toInt()
                } else {
                    lenses.add(Lens(label, value.toInt()))
                }
            } else {
                val label = step.substringBefore("-")
                map[hash(label)]?.removeIf { it.label == label }
            }
        }

        return map.entries.sumOf { (key, value) ->
            value.mapIndexed { index, lens -> (key + 1) * (index + 1) * lens.value }.sum()
        }
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
