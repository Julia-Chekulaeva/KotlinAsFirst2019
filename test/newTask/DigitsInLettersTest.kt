package newTask

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class DigitsInLettersTest {

    @Test
    fun foo() {
        val digitsInLetters = DigitsInLetters()
        val compareTo = digitsInLetters.foo("23:59", "00:02")
        assertEquals(
            "ABDEG ABCDG:ACDFG ABCDFG, ABCDEF ABCDEF:ABCDEF ABCDEF, ABCDEF ABCDEF:ABCDEF BC, ABCDEF ABCDEF:ABCDEF ABDEG",
            compareTo
        )
    }
}