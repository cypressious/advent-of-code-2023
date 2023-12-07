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
    val labels = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

    data class Entry(val hand: String, val bid: Int) {
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

        override fun toString() =
            "Entry(hand='$hand', bid=$bid, counts=$counts, type=$type)"
    }

    fun part1(input: List<String>): Int {
        val entries = input.mapTo(mutableListOf()) {
            val (hand, bid) = it.split(" ")
            Entry(hand, bid.toInt())
        }

        entries.sortWith(compareByDescending<Entry> { it.type }
            .thenByDescending { labels.indexOf(it.hand[0]) }
            .thenByDescending { labels.indexOf(it.hand[1]) }
            .thenByDescending { labels.indexOf(it.hand[2]) }
            .thenByDescending { labels.indexOf(it.hand[3]) }
            .thenByDescending { labels.indexOf(it.hand[4]) }
        )

        return entries.withIndex().sumOf { (i, entry) -> (i + 1) * entry.bid }
    }


    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
