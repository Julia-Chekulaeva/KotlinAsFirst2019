package task_1

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

class MyFilesTests {

    @Test
    fun myFun() {
        val calendar = Calendar()
        calendar.myFun(
            "C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\MyFile",
            Data(1, 25), Data(4, 3)
        )
    }

    @Test
    fun foo() {
        val myFile2 = MyFile2()
        myFile2.foo(
            "C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\someText",
            "C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\someTextOutput"
        )
        assertEquals(
            File("C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\someTextOutput").readText(),
            File("C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\correctOutput").readText()
        )
    }

    @Test
    fun otherFoo() {
        val myFile2 = MyFile2()
        myFile2.otherFoo(
            "C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\someText",
            "C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\someTextOutput2"
        )
        assertEquals(
            File("C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\someTextOutput2").readText(),
            File("C:\\Users\\Юлия\\IdeaProjects\\KotlinAsFirst2019\\test\\task_1\\correctOutput2").readText()
        )
    }
}