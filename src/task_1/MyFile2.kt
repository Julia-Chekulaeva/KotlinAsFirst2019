package task_1

import java.io.File

class MyFile2 {

    fun foo(inputName: String, outputName: String) {
        val text = File(inputName).readText()
        val lines = text.split("$$")
        File(outputName).bufferedWriter().use {
            for (line in lines) {
                it.write(line)
                it.newLine()
            }
        }
    }

    fun otherFoo(inputName: String, outputName: String) {
        val text = File(inputName).readText()
        File(outputName).bufferedWriter().use {
            var removingWord = false
            var waitingWhileNotWord = false
            var removingText = false
            loop@ for (char in text) {
                when (char) {
                    '#' -> {
                        removingWord = true
                        waitingWhileNotWord = true
                    }
                    '@' -> removingText = !removingText
                    else -> {
                        if (removingText) continue@loop
                        if (removingWord) {
                            val isLetterOrDigit = "$char".matches(Regex("""[\wа-яА-Я\d]"""))
                            if (isLetterOrDigit) {
                                waitingWhileNotWord = false
                                continue@loop
                            }
                            if (!isLetterOrDigit)
                                it.write(char.toString())
                            if (!(isLetterOrDigit || waitingWhileNotWord))
                                removingWord = false
                        } else {
                            it.write(char.toString())
                        }
                    }
                }
            }
        }
    }
}