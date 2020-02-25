package task_1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class PolynomTests {
    @Test
    @Tag("Example")
    fun constructor() {
        assertEquals(listOf(1.0, 3.0, 0.0), Polynominal(0.0, 0.0, 1.0, 3.0, 0.0).coefficients)
        assertEquals(listOf(1.0, 3.0, 0.0), Polynominal(1.0, 3.0, 0.0).coefficients)
        assertEquals(listOf(0.0), Polynominal().coefficients)
        assertEquals(listOf(0.0), Polynominal(0.0).coefficients)
        assertEquals(listOf(0.0), Polynominal(0.0, 0.0).coefficients)
    }

    @Test
    @Tag("Example")
    fun plus() {
        assertEquals(
            Polynominal(2.0, 1.0, 5.0),
            Polynominal(1.0, 3.0, 5.0) + Polynominal(1.0, -2.0, 0.0)
        )
        assertEquals(
            Polynominal(4.0, 3.0, 2.0, 3.0),
            Polynominal(4.0, 1.0, 5.0, 1.0) + Polynominal(2.0, -3.0, 2.0)
        )
        assertEquals(
            Polynominal(4.0, 3.0, 2.0, 3.0),
            Polynominal(2.0, -3.0, 2.0) + Polynominal(4.0, 1.0, 5.0, 1.0)
        )
        assertEquals(
            Polynominal(1.0, 5.0),
            Polynominal(1.0, 3.0, 5.0) + Polynominal(-1.0, -2.0, 0.0)
        )
    }

    @Test
    @Tag("Example")
    fun minus() {
        assertEquals(
            Polynominal(1.0, 5.0),
            Polynominal(1.0, 3.0, 5.0) - Polynominal(1.0, 2.0, 0.0)
        )
    }

    @Test
    @Tag("Example")
    fun times() {
        assertEquals(
            Polynominal(1.0, -3.0, 3.0, -1.0),
            Polynominal(1.0, -1.0) * Polynominal(1.0, -2.0, 1.0)
        )
    }

    @Test
    @Tag("Example")
    fun div() {
        assertEquals(
            Polynominal(1.0, -1.0),
            Polynominal(1.0, -3.0, 3.0, -1.0) / Polynominal(1.0, -2.0, 1.0)
        )
        assertEquals(
            Polynominal(1.0, -2.0, 1.0),
            Polynominal(1.0, -3.0, 3.0, -1.0) / Polynominal(1.0, -1.0)
        )
        assertEquals(
            Polynominal(1.0, -2.0, 0.0),
            Polynominal(1.0, -3.0, 2.0, 1.0) / Polynominal(1.0, -1.0)
        )
        assertEquals(
            Polynominal(1.0, -1.0),
            Polynominal(1.0, -3.0, 2.0, 1.0) / Polynominal(1.0, -2.0, 1.0)
        )
    }

    @Test
    @Tag("Example")
    fun mod() {
        assertEquals(
            Polynominal(0.0),
            Polynominal(1.0, -3.0, 3.0, -1.0) % Polynominal(1.0, -1.0)
        )
        assertEquals(
            Polynominal(0.0),
            Polynominal(1.0, -3.0, 3.0, -1.0) % Polynominal(1.0, -2.0, 1.0)
        )
        assertEquals(
            Polynominal(1.0),
            Polynominal(1.0, -3.0, 2.0, 1.0) % Polynominal(1.0, -1.0)
        )
        assertEquals(
            Polynominal(-1.0, 2.0),
            Polynominal(1.0, -3.0, 2.0, 1.0) % Polynominal(1.0, -2.0, 1.0)
        )
    }

    @Test
    @Tag("Example")
    fun valueOfExpression() {
        assertEquals(0.0, Polynominal(1.0, 3.0, 3.0, 1.0).valueOfExpression(-1))
        assertEquals(1.0, Polynominal(1.0, 3.0, 1.0).valueOfExpression(0))
        assertEquals(0.0, Polynominal(0.0).valueOfExpression(9))
    }

    @Test
    @Tag("Example")
    fun equals() {
        assertEquals(
            true,
            Polynominal(8.0, 7.0, -1.0, 0.0) == Polynominal(8.0, 7.0, -1.0, 0.0)
        )
        assertEquals(
            true,
            Polynominal(1.0, 3.0, 0.0) == Polynominal(0.0, 0.0, 1.0, 3.0, 0.0)
        )
        assertEquals(
            true,
            Polynominal(0.0) == Polynominal()
        )
        assertEquals(
            false,
            Polynominal(1.0, 3.0) == Polynominal(1.0, 3.0, 0.0)
        )
    }

    @Test
    @Tag("Example")
    fun toStringOverride() {
        assertEquals("0.0", Polynominal().toString())
        assertEquals(
            "9.0 * x^3 + 1.0 * x^2 - 8.0 * x^1 + 3.0",
            Polynominal(9.0, 1.0, -8.0, 3.0).toString()
        )
        assertEquals(
            "-1.0 * x^2 - 8.0 * x^1 + 3.0",
            Polynominal(-1.0, -8.0, 3.0).toString()
        )
    }
}