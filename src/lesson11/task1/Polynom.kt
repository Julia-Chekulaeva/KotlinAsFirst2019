@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
fun notEmptyList(list: List<Double>) = if (list.isEmpty()) listOf(0.0) else list

class Polynom(vararg coeffs: Double) {

    val coefficients = notEmptyList(coeffs.toList().dropWhile { it == 0.0 })

    private val reversedCoeffs = coefficients.reversed()

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = coefficients[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double = coefficients.fold(0.0) { prev, coeff ->
        prev * x + coeff
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = coefficients.lastIndex

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val list = other.reversedCoeffs
        return if (degree() > other.degree()) Polynom(*(list.withIndex().map { it.value + reversedCoeffs[it.index] }
                + reversedCoeffs.subList(other.degree() + 1, degree() + 1)).reversed().toDoubleArray())
        else Polynom(*(reversedCoeffs.withIndex().map { it.value + list[it.index] }
                + list.subList(degree() + 1, other.degree() + 1)).reversed().toDoubleArray())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(*coefficients.map { -it }.toDoubleArray())

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = plus(-other)

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val list = MutableList(degree() + other.degree() + 1) { 0.0 }
        val list1 = reversedCoeffs.withIndex()
        val list2 = other.reversedCoeffs.withIndex()
        for ((i, coeff1) in list1)
            for ((j, coeff2) in list2)
                list[i + j] += coeff1 * coeff2
        return Polynom(*list.reversed().toDoubleArray())
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    private fun division(other: Polynom): Pair<Polynom, Polynom> {
        val dividedPolynomCoeffs = coefficients.toMutableList()
        val divisiorCoeffs = other.coefficients
        val firstDivCoeff = divisiorCoeffs[0]
        val list = mutableListOf<Double>()
        for (i in degree() - other.degree() downTo 0) {
            val a = dividedPolynomCoeffs[0] / firstDivCoeff
            list.add(a)
            divisiorCoeffs.withIndex().forEach { dividedPolynomCoeffs[it.index] -= it.value * a }
            dividedPolynomCoeffs.removeAt(0)
        }
        return Polynom(*list.toDoubleArray()) to Polynom(*dividedPolynomCoeffs.toDoubleArray())
    }

    operator fun div(other: Polynom) = division(other).first

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom) = division(other).second

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?) = other is Polynom && other.coefficients == coefficients

    /**
     * Получение хеш-кода
     */
    override fun hashCode() = coefficients.hashCode()
}
