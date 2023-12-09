fun main() {
    data class Node(val name: String) {
        lateinit var left: Node
        lateinit var right: Node
    }

    fun parseInput(input: List<String>): Pair<String, Map<String, Node>> {
        val instructions = input[0]
        val nodes = mutableMapOf<String, Node>()

        for (line in input.drop(2)) {
            val (from, l, r) = line.dropLast(1).split(" = (", ", ")
            nodes.getOrPut(from) { Node(from) }.run {
                left = nodes.getOrPut(l) { Node(l) }
                right = nodes.getOrPut(r) { Node(r) }
            }
        }

        return instructions to nodes
    }

    fun part1(input: List<String>): Int {
        val (instructions, nodes) = parseInput(input)

        var count = 0

        var current = nodes.getValue("AAA")
        val end = nodes.getValue("ZZZ")

        while (current != end) {
            current = if (instructions[count % instructions.length] == 'L') {
                current.left
            } else {
                current.right
            }

            count++
        }

        return count
    }

    fun gcd(a: Long, b: Long): Long {
        var aNew = a
        var bNew = b
        while (bNew > 0) {
            val temp = bNew
            bNew = aNew % bNew
            aNew = temp
        }
        return aNew
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    fun part2(input: List<String>): Long {
        val (instructions, nodes) = parseInput(input)

        var count = 0

        var current = nodes.filterKeys { it.endsWith("A") }.values
        val periods = arrayOfNulls<Int>(current.size)

        while (!periods.all { it != null }) {
            for ((i, node) in current.withIndex()) {
                if (node.name.endsWith("Z")) {
                    if (periods[i] == null) {
                        periods[i] = count
                    }
                }
            }

            current = current.map {
                if (instructions[count % instructions.length] == 'L') {
                    it.left
                } else {
                    it.right
                }
            }

            count++
        }

        return periods.fold(1L) { acc, i -> lcm(acc, i!!.toLong()) }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 2)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
