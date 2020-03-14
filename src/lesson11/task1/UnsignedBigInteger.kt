package lesson11.task1

import java.lang.ArithmeticException
import kotlin.math.min

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger private constructor(numberWithZeroes: List<Int>) : Comparable<UnsignedBigInteger> {

    private fun zero(list: List<Int>) = if (list.isEmpty()) listOf(0) else list

    val number = zero(numberWithZeroes.dropLastWhile { it == 0 })
    /**
     * Конструктор из строки
     */
    constructor(s: String) : this(s.map { (it - '0') }.reversed())

    /**
     * Конструктор из целого
     */
    constructor(i: Int) : this(i.toString())

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        val list = mutableListOf<Int>()
        var c = 0
        val otherNumber = other.number
        val min = min(number.size, otherNumber.size)
        val diff = number.size - otherNumber.size
        val maxNumber = if (diff > 0) number else otherNumber
        for (i in 0 until min) {
            val s = c + number[i] + otherNumber[i]
            list.add((s % 10))
            c = s / 10
        }
        if (diff != 0) {
            list.add((c + maxNumber[min]))
            list.addAll(maxNumber.subList(min + 1, min + diff))
        }
        return UnsignedBigInteger(list)
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        val otherNumber = other.number
        if (this < other)
            throw ArithmeticException()
        val list = mutableListOf<Int>()
        var c = 1
        val otherSize = otherNumber.size
        val thisSize = number.size
        for (i in 0 until otherSize) {
            val s = number[i] - otherNumber[i] + 10 + c - 1
            list.add((s % 10))
            c = s / 10
        }
        for (i in otherSize until thisSize) {
            val s = number[i] + 10 + c - 1
            list.add((s % 10))
            c = s / 10
        }
        return UnsignedBigInteger(list)
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        if (other.toString() == "0") return UnsignedBigInteger(0)
        val list1 = number.withIndex()
        val list2 = other.number.withIndex()
        val size = number.size + other.number.size
        val list = MutableList(size) { 0 }
        for ((i, d1) in list1)
            for ((j, d2) in list2)
                list[i + j] += d1 * d2
        for (i in 1 until size) list[i] += list[i - 1] / 10
        for (i in 0 until size) list[i] %= 10
        if (list[size - 1] == 0) list.removeAt(size - 1)
        return UnsignedBigInteger(list)
    }

    operator fun times(other: Int) = this * UnsignedBigInteger(other)/*{
        if (other == 0) return UnsignedBigInteger(0)
        val list = mutableListOf(0)
        for (digit in number) {
            list.add(digit * other)
        }
        var lastIndex = list.lastIndex
        for (i in 0 until lastIndex) {
            list[i + 1] += list[i] / 10
            list[i] %= 10
        }
        while (list[lastIndex] > 0) {
            list.add(list[lastIndex] / 10)
            list[lastIndex] %= 10
            lastIndex = list.lastIndex
        }
        return UnsignedBigInteger(list)
    }*/

    private fun division(other: UnsignedBigInteger): Pair<List<Int>, List<Int>> {
        var dividedNumber = number.reversed()
        val divSize = dividedNumber.size
        val divisior = other.number.reversed()
        val size = divisior.size
        val div = mutableListOf<Int>()
        var diff = UnsignedBigInteger(0)
        val index = dividedNumber.size - divisior.size
        val multiply = mutableListOf<UnsignedBigInteger>()
        for (i in 0..9)
            multiply.add(other * i)
        for (digit in 0..index) {
            val dividedPart = UnsignedBigInteger(dividedNumber.subList(0, size + digit).reversed())
            for (i in 9 downTo 0) {
                if (multiply[i] <= dividedPart) {
                    diff = dividedPart - multiply[i]
                    div.add(i)
                    println("$diff = $dividedPart - ${multiply[i]}")
                    dividedNumber = diff.number.reversed() + dividedNumber.subList(size + digit, dividedNumber.size)
                    dividedNumber = List(divSize - dividedNumber.size) { 0 } + dividedNumber
                    break
                }
            }
        }
        return div.reversed() to diff.number
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger) = UnsignedBigInteger(division(other).first)

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger) = UnsignedBigInteger(division(other).second)

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?) = other is UnsignedBigInteger && number == other.number

    override fun hashCode() = number.hashCode()

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        val a = number.size - other.number.size
        if (a > 0) return 1
        if (a < 0) return -1
        if (a == 0) {
            for ((i, n) in other.number.withIndex().reversed()) {
                if (n == number[i]) continue
                if (number[i] > n) return 1
                return -1
            }
        }
        return 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = number.reversed().joinToString("") { it.toString() }

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        if (this > UnsignedBigInteger(Int.MAX_VALUE)) throw ArithmeticException()
        return number.reversed().fold(0) { prev, num ->
            prev * 10 + num
        }
    }

}