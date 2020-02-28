@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "комплексое число".
 *
 * Общая сложность задания -- лёгкая.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */
    constructor(str: String) : this(
        if (!str.matches(Regex("""\-?\d+(\.\d+)?[+-]\d+(\.\d+)?i"""))) throw IllegalArgumentException()
        else
            str.split("+", "-")[0].toDouble(),
        if (str.contains("+")) str.split("+")[1].removeSuffix("i").toDouble()
        else -str.split("-")[1].removeSuffix("i").toDouble()
    )

    /**
     * Сложение.
     */
    operator fun plus(other: Complex) = Complex(re + other.re, im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus() = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)


    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(re * other.re - im * other.im, im * other.re + re * other.im)


    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {
        val re1 = other.re
        val im1 = other.im
        val d = re1 * re1 + im1 * im1
        return Complex((re * re1 + im * im1) / d, (re1 * im - re * im1) / d)
    }


    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?) = other is Complex && other.im == im && other.re == re

    /**
     * Преобразование в строку
     */
    override fun toString(): String = "$re+${im}i"
}