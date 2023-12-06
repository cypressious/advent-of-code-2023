fun main() {
    data class Mapping(
        val sourceRange: LongRange,
        val destinationStart: Long,
    ) {
        fun map(value: Long): Long {
            val offset = value - sourceRange.first
            return destinationStart + offset
        }
    }

    fun getStages(input: List<String>): List<List<Mapping>> {
        val stages = mutableListOf<MutableList<Mapping>>()
        var mappings = mutableListOf<Mapping>().also { stages += it }

        for (line in input.drop(3)) {
            if (line.isBlank()) {
                mappings = mutableListOf<Mapping>().also { stages += it }
                continue
            }

            if (!line[0].isDigit()) continue

            val (destinationStart, sourceRangeStart, rangeLength) = line.split(" ")
                .map { it.toLong() }
            mappings.add(
                Mapping(
                    sourceRangeStart..<sourceRangeStart + rangeLength,
                    destinationStart,
                )
            )
        }

        stages.forEach { stage -> stage.sortBy { it.sourceRange.first } }

        return stages
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].substringAfter(" ").split(" ").map { it.toLong() }
        val stages = getStages(input)

        return seeds.minOf { seed ->
            var value = seed

            for (stage in stages) {
                val mapping = stage.firstOrNull { value in it.sourceRange } ?: continue
                value = mapping.map(value)
            }

            value
        }
    }

    fun mapRange(range: LongRange, mappings: List<Mapping>): List<LongRange> {
        val result = mutableListOf<LongRange>()
        var pointer = range.first

        while (pointer in range) {
            val mapping =
                mappings.firstOrNull { pointer in it.sourceRange || it.sourceRange.first > pointer }
            if (mapping == null) {
                result.add(pointer..range.last)
                pointer = range.last + 1
                continue
            }

            if (pointer < mapping.sourceRange.first) {
                result.add(pointer..<mapping.sourceRange.first)
                pointer = mapping.sourceRange.first
            }

            val end = minOf(range.last, mapping.sourceRange.last)
            result.add(mapping.map(pointer)..mapping.map(end))

            pointer = end + 1
        }

        return result
    }

    fun part2(input: List<String>): Long {
        var seedRanges = input[0].substringAfter(" ").split(" ").map { it.toLong() }.chunked(2)
            .map { (from, length) -> from..<from + length }
        val stages = getStages(input)

        for (stage in stages) {
            seedRanges = seedRanges.flatMap { mapRange(it, stage) }
        }

        return seedRanges.minOf { it.first }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
