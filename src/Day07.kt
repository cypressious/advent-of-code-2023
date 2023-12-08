enum class HandType {
    Five,
    Four,
    FullHouse,
    Three,
    TwoPair,
    Pair,
    HighCard
}

fun main() {
    data class Entry(val hand: String, val bid: Int, val type: HandType)

    fun comparator(labels: List<Char>) = compareByDescending<Entry> { it.type }
        .thenByDescending { labels.indexOf(it.hand[0]) }
        .thenByDescending { labels.indexOf(it.hand[1]) }
        .thenByDescending { labels.indexOf(it.hand[2]) }
        .thenByDescending { labels.indexOf(it.hand[3]) }
        .thenByDescending { labels.indexOf(it.hand[4]) }

    fun part1(input: List<String>): Int {
        val entries = input.mapTo(mutableListOf()) { line ->
            val (hand, bid) = line.split(" ")
            val counts = hand.groupingBy { it }.eachCount()
            val type = when {
                counts.size == 1 -> HandType.Five
                counts.any { it.value == 4 } -> HandType.Four
                counts.any { it.value == 3 } && counts.any { it.value == 2 } -> HandType.FullHouse
                counts.any { it.value == 3 } -> HandType.Three
                counts.size == 3 -> HandType.TwoPair
                counts.size == 4 -> HandType.Pair
                else -> HandType.HighCard
            }
            Entry(hand, bid.toInt(), type)
        }

        entries.sortWith(
            comparator(listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'))
        )

        return entries.withIndex().sumOf { (i, entry) -> (i + 1) * entry.bid }
    }

    fun part2(input: List<String>): Long {
        val entries = input.mapTo(mutableListOf()) { line ->
            val (hand, bid) = line.split(" ")
            val counts = hand.groupingBy { it }.eachCount()
            val jokers = counts.getOrDefault('J', 0)

            fun Map.Entry<Char, Int>.canBe(result: Int): Boolean {
                return value == result || key != 'J' && value + jokers == result
            }

            val type = when {
                counts.any { it.canBe(5) } -> HandType.Five
                counts.any { it.canBe(4) } -> HandType.Four
                counts.size == 2 || counts.size == 3 && jokers > 0 -> HandType.FullHouse
                counts.any { it.canBe(3) } -> HandType.Three
                counts.size == 3 && jokers == 0 -> HandType.TwoPair
                counts.any { it.canBe(2) } -> HandType.Pair
                else -> HandType.HighCard
            }
            Entry(hand, bid.toInt(), type)
        }

        entries.sortWith(
            comparator(listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'))
        )

        return entries.withIndex().sumOf { (i, entry) -> (i + 1L) * entry.bid }

    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
