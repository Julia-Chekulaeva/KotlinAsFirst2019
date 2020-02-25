package task_1

fun notEmptyList(list: List<Double>) = if (list.isEmpty()) listOf(0.0) else list

data class Polynominal private constructor(val coefficients: List<Double>) {

    constructor(vararg coeffsWithZerous: Double) :
            this(notEmptyList(coeffsWithZerous.dropWhile { it < 10000 * Math.ulp(it) && it > -10000 * Math.ulp(it) }))

    val degree = coefficients.lastIndex

    private val reversedCoeffs = coefficients.reversed()

    operator fun plus(other: Polynominal): Polynominal {
        val list = other.reversedCoeffs
        return if (degree > other.degree) Polynominal(*(list.withIndex().map { it.value + reversedCoeffs[it.index] }
                + reversedCoeffs.subList(other.degree + 1, degree + 1)).reversed().toDoubleArray())
        else Polynominal(*(reversedCoeffs.withIndex().map { it.value + list[it.index] }
                + list.subList(degree + 1, other.degree + 1)).reversed().toDoubleArray())
    }

    operator fun minus(other: Polynominal) = plus(Polynominal(other.coefficients.map { -it }))

    operator fun times(a: Double) = Polynominal(*coefficients.map { it * a }.toDoubleArray())

    operator fun times(other: Polynominal): Polynominal {
        val list = MutableList(degree + other.degree + 1) { 0.0 }
        val list1 = reversedCoeffs.withIndex()
        val list2 = other.reversedCoeffs.withIndex()
        for ((i, coeff1) in list1)
            for ((j, coeff2) in list2)
                list[i + j] += coeff1 * coeff2
        return Polynominal(*list.reversed().toDoubleArray())
    }

    private fun division(other: Polynominal): Pair<Polynominal, Polynominal> {
        val dividedPolynomCoeffs = coefficients.toMutableList()
        val divisiorCoeffs = other.coefficients
        val firstDivCoeff = divisiorCoeffs[0]
        val list = mutableListOf<Double>()
        for (i in degree - other.degree downTo 0) {
            val a = dividedPolynomCoeffs[0] / firstDivCoeff
            list.add(a)
            divisiorCoeffs.withIndex().forEach { dividedPolynomCoeffs[it.index] -= it.value * a }
            dividedPolynomCoeffs.removeAt(0)
        }
        return Polynominal(*list.toDoubleArray()) to Polynominal(*dividedPolynomCoeffs.toDoubleArray())
    }

    operator fun div(other: Polynominal) = division(other).first

    operator fun rem(other: Polynominal) = division(other).second

    fun valueOfExpression(x: Int) = coefficients.fold(0.0) { prev, coeff ->
        prev * x + coeff
    }

    override fun equals(other: Any?): Boolean {
        require(other is Polynominal)
        return coefficients == other.coefficients
    }

    override fun hashCode() = coefficients.hashCode()

    override fun toString() =
        coefficients.subList(1, degree + 1).withIndex().joinToString(
            "",
            "${coefficients[0]} * x^$degree"
        ) {
            if (it.value >= 0.0) " + ${it.value} * x^${degree - 1 - it.index}"
            else " - ${-it.value} * x^${degree - 1 - it.index}"
        }.removeSuffix(" * x^0")
}