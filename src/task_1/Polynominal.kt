package task_1

data class Polynominal private constructor(val coefficients: List<Double>) {

    constructor(vararg coeffsWithZerous: Double) :
            this(coeffsWithZerous.dropWhile { it < 10000 * Math.ulp(it) && it > -10000 * Math.ulp(it) })

    val degree = coefficients.lastIndex

    val reversedCoeffs = coefficients.reversed()

    operator fun plus(other: Polynominal): Polynominal {
        val list = other.reversedCoeffs
        return if (degree > other.degree) Polynominal((list.withIndex().map { it.value + reversedCoeffs[it.index] }
                + reversedCoeffs.subList(other.degree, degree)).reversed())
        else Polynominal((reversedCoeffs.withIndex().map { it.value + list[it.index] }
                + list.subList(degree, other.degree)).reversed())
    }

    operator fun minus(other: Polynominal): Polynominal = plus(Polynominal(other.coefficients.map { -it }))

    operator fun times(a: Double) = Polynominal(coefficients.map { it * a })

    operator fun times(other: Polynominal): Polynominal {
        val list = MutableList(degree + other.degree + 1) { 0.0 }
        val list1 = reversedCoeffs.withIndex()
        val list2 = other.reversedCoeffs.withIndex()
        for ((i, coeff1) in list1)
            for ((j, coeff2) in list2)
                list[i + j] += coeff1 * coeff2
        return Polynominal(list.reversed())
    }

    private fun division(other: Polynominal): Pair<Polynominal, Polynominal> {
        val dividedPolynCoeffs = coefficients.toMutableList()
        val divisiorCoeff = other.coefficients[0]
        val list = mutableListOf<Double>()
        for (i in degree - other.degree downTo 0) {
            val a = dividedPolynCoeffs[0] / divisiorCoeff
            list.add(a)
            other.coefficients.withIndex().forEach { dividedPolynCoeffs[it.index] -= it.value * a }
            dividedPolynCoeffs.removeAt(0)
        }
        return Polynominal(list) to Polynominal(dividedPolynCoeffs)
    }

    operator fun div(other: Polynominal): Polynominal = division(other).first

    operator fun rem(other: Polynominal): Polynominal = division(other).second

    fun valueOfExpression(x: Int) = coefficients.fold(0.0) { prev, coeff ->
        prev * x + coeff
    }

    override fun equals(other: Any?): Boolean {
        //return super.equals(other) // Что это?
        require(other is Polynominal)
        return coefficients == other.coefficients
    }

    override fun hashCode() = coefficients.hashCode()

    override fun toString() =
        coefficients.withIndex().joinToString(" + ") { "${it.value} * x^${degree - it.index}" }.removeSuffix(" * x^0") // Мда, не идеально
}
/*(StringBuilder("${coefficients[0]} * x^$degree") {
        str, elem ->
        {
            str.append("${elem.value} * x^${degree - elem.index}")
        }
    }.toString()*/