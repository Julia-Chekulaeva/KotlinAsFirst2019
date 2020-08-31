package newTask

import kotlinx.html.I
import java.lang.IllegalArgumentException

val digitNames = listOf("ABCDEF", "BC", "ABDEG", "ABCDG", "BCFG", "ACDFG", "ACDEFG", "ABC", "ABCDEFG", "ABCDFG")

class DigitsInLetters {

    fun foo(tStart: String, tEnd: String): String {
        val start = timeFromString(tStart).timeToFullMinutes()
        val end = timeFromString(tEnd).timeToFullMinutes()
        val listOfTimes = mutableListOf<Time>()
        if (start <= end) {
            for (i in start..end) {
                listOfTimes.add(Time(i / 60, i % 60))
            }
        } else {
            val max = 23 * 60 + 59
            for (i in start..max) {
                listOfTimes.add(Time(i / 60, i % 60))
            }
            for (i in 0..end) {
                listOfTimes.add(Time(i / 60, i % 60))
            }
        }
        val res = listOfTimes.joinToString(", ") { it.toString() }
        println(res)
        return res
    }
}

fun timeFromString(time: String): Time {
    if (!time.matches("""\d\d:\d\d""".toRegex()))
        throw IllegalArgumentException("Неправильный формат строки")
    val split = time.split(":")
    return Time(split[0].toInt(), split[1].toInt())
}

class Time(val hours: Int, val minutes: Int) {

    init {
        if (hours !in 0..23 || minutes !in 0..59) {
            throw IllegalArgumentException("Неправильное время")
        }
    }

    fun timeToFullMinutes() = hours * 60 + minutes

    override fun toString(): String {
        val hourFirstDigit = hours / 10
        val hourSecondDigit = hours % 10
        val minuteFirstDigit = minutes / 10
        val minuteSecondDigit = minutes % 10
        return digitNames[hourFirstDigit] + " " + digitNames[hourSecondDigit] + ":" +
                digitNames[minuteFirstDigit] + " " + digitNames[minuteSecondDigit]
    }
}