import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.io.File

class TestsPackPLE {

    @Test
    @Tag("Example")
    fun utilite() {
        utilite("pack-rle -z -out input/task2_input/resOfTask2.txt input/task2_input/task2.txt")
        utilite("pack-rle -z input/task2_input/task2.txt")
        utilite("pack-rle -u -out input/task2_input/resOfTask2RLE.txt input/task2_input/task2RLE.txt")
        utilite("pack-rle -u input/task2_input/task2RLE.txt")
        assertEquals(
            File("input/task2_input/task2RLE.rle").readText(), File("input/task2_input/resOfTask2RLE.txt").readText()
        )
        assertEquals(
            File("input/task2_input/task2.rle").readText(), File("input/task2_input/resOfTask2.txt").readText()
        )
        assertEquals(
            File("input/task2_input/task2.txt").readText(), File("input/task2_input/resOfTask2RLE.txt").readText()
        )
        assertEquals(
            File("input/task2_input/task2RLE.txt").readText(), File("input/task2_input/resOfTask2.txt").readText()
        )
    }

}