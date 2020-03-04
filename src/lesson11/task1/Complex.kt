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
            str.removePrefix("-").removeSuffix("i").split("+", "-"), str[0] != '-', str.contains("+")
    )

    private constructor(list: List<String>, sign1: Boolean, sign2: Boolean) : this(
        if (sign1) list[0].toDouble() else -list[0].toDouble(),
        if (sign2) list[1].toDouble() else -list[1].toDouble()
    )

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

    override fun hashCode() = re.hashCode() + im.hashCode()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = if (re % 1.0 == 0.0) {
        if (im >= 0.0) "${re.toInt()}+${im.toInt()}i" else "${re.toInt()}${im.toInt()}i"
    } else {
        if (im >= 0.0) "$re+${im}i" else "$re${im}i"
    }
}