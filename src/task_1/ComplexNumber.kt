package task_1

//import kotlin.IllegalArgumentException

data class ComplexNumber constructor(val x: Double, val y: Double) {

    constructor(number: Pair<Double, Double>) : this(number.first, number.second)

    constructor(str: String) : this(
        str.split(" * i")[0].toDoubleOrNull() ?: throw IllegalArgumentException(),
        str.split(" * i + ", " * i - ")[1].toDoubleOrNull() ?: throw IllegalArgumentException()
    )
    // Для корректного использования конструктора в этом случае все же нужна корректная строка

    operator fun plus(other: ComplexNumber) = ComplexNumber(x + other.x to y + other.y)

    operator fun minus(other: ComplexNumber) = ComplexNumber(x - other.x to y - other.y)

    operator fun times(other: ComplexNumber) = ComplexNumber(x * other.y + y * other.x to y * other.y - x * other.x)

    operator fun div(other: ComplexNumber): ComplexNumber {
        val x1 = other.x
        val y1 = other.y
        val d = x1 * x1 + y1 * y1
        return ComplexNumber((y1 * x - y * x1) / d, (x * x1 + y * y1) / d)
    }

    override fun toString(): String = if (y >= 0.0) "$x * i + $y" else "$x * i - ${-y}"

    override fun equals(other: Any?): Boolean {
        require(other is ComplexNumber)
        return hashCode() == other.hashCode() && x == other.x && y == other.y
    }

    override fun hashCode(): Int = x.hashCode() * 31 + y.hashCode()
}