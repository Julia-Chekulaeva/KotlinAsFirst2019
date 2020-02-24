package task_1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class ComplexNumberTests {
    @Test
    @Tag("Example")
    fun plus() {
        assertEquals(
            ComplexNumber(1.0, 3.0),
            ComplexNumber(2.0, 1.0) + ComplexNumber(-1.0, 2.0)
        )
    }

    @Test
    @Tag("Example")
    fun minus() {
        assertEquals(
            ComplexNumber(1.0, -1.0),
            ComplexNumber(2.0, 1.0) - ComplexNumber(1.0, 2.0)
        )
    }

    @Test
    @Tag("Example")
    fun times() {
        assertEquals(
            ComplexNumber(5.0, 1.0),
            ComplexNumber(-1.0, 1.0) * ComplexNumber(3.0, -2.0)
        )
        assertEquals(
            ComplexNumber(1.0, 8.0),
            ComplexNumber(2.0, 1.0) * ComplexNumber(-3.0, 2.0)
        )
    }

    @Test
    @Tag("Example")
    fun div() {
        assertEquals(
            ComplexNumber(-1.0, 1.0),
            ComplexNumber(5.0, 1.0) / ComplexNumber(3.0, -2.0)
        )
        assertEquals(
            ComplexNumber(3.0, -2.0),
            ComplexNumber(5.0, 1.0) / ComplexNumber(-1.0, 1.0)
        )
        assertEquals(
            ComplexNumber(2.0, 1.0),
            ComplexNumber(1.0, 8.0) / ComplexNumber(-3.0, 2.0)
        )
        assertEquals(
            ComplexNumber(-3.0, 2.0),
            ComplexNumber(1.0, 8.0) / ComplexNumber(2.0, 1.0)
        )
    }

    @Test
    @Tag("Example")
    fun toStringOverride() {
        assertEquals("1.0 * i + 3.0", ComplexNumber(1.0, 3.0).toString())
        assertEquals("-1.0 * i - 3.0", ComplexNumber(-1.0, -3.0).toString())
    }

    @Test
    @Tag("Example")
    fun constructor() {
        assertEquals(ComplexNumber(1.0, 3.0), ComplexNumber("1.0 * i + 3.0"))
        assertEquals(ComplexNumber(-1.0, -3.0), ComplexNumber("-1.0 * i - 3.0"))
    }

    @Test
    @Tag("Example")
    fun equals() {
        assertEquals(true, ComplexNumber(0.7, 8.0) == ComplexNumber(0.7, 8.0))
        assertEquals(false, ComplexNumber(4.0, 6.0) == ComplexNumber(4.0, 8.0))
        assertEquals(false, ComplexNumber(4.0, 6.0) == ComplexNumber(3.0, 6.0))
        assertEquals(false, ComplexNumber(2.0, 6.0) == ComplexNumber(4.0, 8.0))
        assertEquals(false, ComplexNumber(0.7, 8.0) != ComplexNumber(0.7, 8.0))
        assertEquals(true, ComplexNumber(4.0, 6.0) != ComplexNumber(4.0, 8.0))
        assertEquals(true, ComplexNumber(4.0, 6.0) != ComplexNumber(3.0, 6.0))
        assertEquals(true, ComplexNumber(2.0, 6.0) != ComplexNumber(4.0, 8.0))
    }
}