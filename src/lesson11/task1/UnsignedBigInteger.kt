package lesson11.task1

import java.lang.ArithmeticException
import kotlin.math.max
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
class UnsignedBigInteger(val number: List<Int>) : Comparable<UnsignedBigInteger> {

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
        val min = min(number.size, otherNumber.size)
        val diff = number.size - otherNumber.size
        val maxNumber = if (diff > 0) number else otherNumber.map { -it }
        for (i in 0 until min) {
            val s = number[i] - otherNumber[i] + 10 + c - 1
            list.add((s % 10))
            c = s / 10
        }
        if (diff != 0) {
            list.add((c + maxNumber[min] - 1))
            list.addAll(maxNumber.subList(min + 1, min + diff))
        }
        return UnsignedBigInteger(list.dropLastWhile { it == 0 })
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
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

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean = other is UnsignedBigInteger && number == other.number

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        val diff = Int.MAX_VALUE.toString().lastIndex
        return this.toString().substring(max(number.size - diff, 0)).toInt() -
                other.toString().substring(max(other.number.size - diff, 0)).toInt()
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
        val maxInt = Int.MAX_VALUE.toString().map { it.toInt() }
        val a = number.size - maxInt.size
        if (a > 0) throw ArithmeticException()
        if (a == 0) {
            for ((i, n) in number.withIndex()) {
                if (n == maxInt[i]) continue
                if (n > maxInt[i]) throw ArithmeticException()
                break
            }
        }
        return number.reversed().fold(0) { prev, num ->
            prev * 10 + num
        }
    }

}