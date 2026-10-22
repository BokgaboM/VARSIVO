
package com.varsivo.app.explore

object ApsCalculator {

    const val REQUIRED_SUBJECTS = 6

    fun pointsFor(mark: Int): Int = when {
        mark >= 80 -> 7
        mark >= 70 -> 6
        mark >= 60 -> 5
        mark >= 50 -> 4
        mark >= 40 -> 3
        mark >= 30 -> 2
        else -> 1
    }

    fun total(marks: List<Int>): Int =
        marks.sortedDescending()
            .take(REQUIRED_SUBJECTS)
            .sumOf { pointsFor(it) }
}