package task_1

import java.io.File
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

val monthToDay: Map<Int, Int> = setMonthToDay()

fun daysInMonth(month: Int): Int {
    return when (month) {
        1 -> 31
        2 -> 29
        in 3..7 -> {
            if (month % 2 == 1) 31
            else 30
        }
        in 8..12 -> {
            if (month % 2 == 0) 31
            else 30
        }
        else -> throw IllegalArgumentException("Неверный номер месяца")
    }
}

fun setMonthToDay(): Map<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    map[1] = 0
    for (i in 1..11) {
        map[i + 1] = map[i]!! + daysInMonth(i)
    }
    return map
}

fun dayToData(day: Int): Data {
    if (day !in 1..366) return Data(-1, -1)
    for ((month, data) in monthToDay.toList().sortedBy { -it.first }) {
        if (data < day) {
            return Data(month, day - data)
        }
    }
    throw IllegalArgumentException("Что-то в коде недоработано")
}

fun dataFromString(string: String): Data {
    val split = string.split(".")
    return Data(split[1].toInt(), split[0].toInt())
}

class Calendar {

    fun myFun(fileName: String, tStart: Data, tEnd: Data) {
        val text = File(fileName).readText()
        if (!text.matches(Regex("""\d\d?\.\d\d(,\s*\d\d?\.\d\d)*"""))) throw IllegalArgumentException("Неправильный формат текста")
        val datesFromFile = text.split(",").map { dataFromString(it.trim()) }
        val listOfDates = mutableListOf<Data>()
        for (i in tStart.dataToDay()..tEnd.dataToDay()) {
            if (i % 7 !in 4..5) {
                val data = dayToData(i)
                if (!datesFromFile.map { it.dataToDay() }.contains(i)) {
                    listOfDates.add(data)
                }
            }
        }
        //
        println(listOfDates.joinToString(", ") { String.format("%02d.%02d", it.number, it.month) })
        //
        val weekDays =
            listOf(mutableListOf<Data?>(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
        val weekDay = (listOfDates[0].dataToDay() + 1) % 7 // +1 (а не 2), т.к. weekDays начин. с 0
        for (day in 0 until weekDay) {
            weekDays[day].add(null)
        }
        var month = listOfDates[0].month
        for (dayNumber in tStart.dataToDay()..tEnd.dataToDay()) {
            val data = dayToData(dayNumber)
            if (data.month > month) {
                month = data.month
                for (i in 0..4) {
                    weekDays[i].add(null)
                }
            }
            if (dayNumber !in listOfDates.map { it.dataToDay() }) {
                if (dayNumber % 7 !in 4..5) {
                    weekDays[(dayNumber + 1) % 7].add(null)
                }
                continue
            }
            weekDays[(dayNumber + 1) % 7].add(data)
        }
        val writer = File("C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\MyFileOutput").bufferedWriter()
        for ((index, weekDates) in weekDays.withIndex()) {
            val sb = StringBuilder()
            val dayName = when (index) {
                0 -> "ПН  "
                1 -> "ВТ  "
                2 -> "СР  "
                3 -> "ЧТ  "
                4 -> "ПТ  "
                else -> ""
            }
            sb.append(dayName)
            for (data in weekDates) {
                if (data == null) sb.append("    ")
                else sb.append(String.format("  %2d", data.number))
            }
            writer.write(sb.toString())
            writer.newLine()
            println(sb.toString())
        }
        writer.close()
    }
}

class Data(val month: Int, val number: Int) {
    init {
        if (month !in 1..12 || number !in 1..daysInMonth(month)) {
            throw IllegalArgumentException("Несуществующая дата")
        }
    }

    fun dataToDay(): Int = monthToDay[month]!! + number
}

