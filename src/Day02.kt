fun main() {
    data class Subset(val r: Int, val g: Int, val b: Int)
    data class Game(val id: Int, val subsets: List<Subset>)

    fun toGame(line: String): Game {
        val (a, b) = line.split(":")
        val id = a.substringAfter(" ").toInt()
        val subsets = b.split(";").map {
            Subset(
                """(\d+) red""".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0,
                """(\d+) green""".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0,
                """(\d+) blue""".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0,
            )
        }
        return Game(id, subsets)
    }

    fun part1(input: List<String>): Int {
        val games = input.map(::toGame)
        return games.filter { g ->
            g.subsets.all { it.r <= 12 && it.g <= 13 && it.b <= 14 }
        }.sumOf { it.id }
    }


    fun part2(input: List<String>): Int {
        val games = input.map(::toGame)
        return games.sumOf {
            it.subsets.maxOf { it.r } * it.subsets.maxOf { it.g } * it.subsets.maxOf { it.b }
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
