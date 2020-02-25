package task_1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class PolynomTests {
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
        )// Почему тест ниже проходит, а этот - нет...
        assertEquals(Polynominal(1.0, 3.0, 0.0), Polynominal(0.0, 0.0, 1.0, 3.0, 0.0))
    }
}