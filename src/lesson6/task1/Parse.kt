@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.text.NumberFormat

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = listOf(
    "января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
    "октября", "ноября", "декабря"
)

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val num = parts[0].toIntOrNull()
    val year = parts[2].toIntOrNull()
    val month = months.indexOf(parts[1]) + 1
    if (num ?: 0 in 1..lesson2.task2.daysInMonth(month, year ?: 0) && year != null)
        return String.format("%02d.%02d.%d", num, month, year)
    return ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val num = parts[0].toIntOrNull()
    val month = parts[1].toIntOrNull()
    val year = parts[2].toIntOrNull()
    if (num ?: 0 in 1..lesson2.task2.daysInMonth(month ?: 0, year ?: 0) && year != null)
        return "$num ${months[month!! - 1]} $year"
    return ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */

fun flattenPhoneNumber(phone: String): String {
    val s = phone.filter { it != ' ' && it != '-' }
    if (s.matches(Regex("""(\+\d+)?(\(\d+\))?\d+"""))) return s.filter { it != '(' && it != ')' }
    return ""
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val attempts = jumps.split(" ").filter { it != "-" && it != "%" }
    var max = -1
    for (elem in attempts) {
        val s = elem.toIntOrNull() ?: return -1
        max = maxOf(max, s)
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!jumps.matches(Regex("""(([1-9]\d*|0) %*[-+%])( ([1-9]\d*|0) %*[-+%])*"""))
        || !jumps.contains('+')
    ) return -1
    val s = jumps.split(" ")
    var max = 0
    for (i in 0 until s.size / 2) {
        if (s[2 * i + 1].last() == '+') max = maxOf(max, s[2 * i].toInt())
    }
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */

fun plusMinus(expression: String): Int {
    if (!expression.matches(Regex("""([1-9]\d*|0)( (\+|-) ([1-9]\d*|0))*""")))
        throw IllegalArgumentException()
    println()
    val counter = expression.split(" ")
    var sum = counter[0].toInt()
    for (i in 1..counter.size / 2) {
        val c = counter[2 * i].toInt()
        when (counter[2 * i - 1]) {
            "+" -> sum += c
            "-" -> sum -= c
        }
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val words = str.toLowerCase().split(" ")
    var index = 0
    for (i in 0 until words.lastIndex) {
        if (words[i] == words[i + 1]) return index
        index += words[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val s = description.split("; ")
    var maxSum = 0.0
    var bestGood = ""
    for (elem in s) {
        val goodPrice = elem.split(" ")
        if (goodPrice.size != 2) return ""
        val price = goodPrice[1].toDoubleOrNull() ?: return ""
        if (price >= maxSum) {
            maxSum = price
            bestGood = goodPrice[0]
        }
    }
    return bestGood
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */

fun fromRoman(roman: String): Int {
    if (!roman.matches(Regex("""M{0,3}((D?C{0,3})|(CD)|(CM))((L?X{0,3})|(XL)|(XC))((V?I{0,3})|(IV)|(IX))"""))
        || roman.isEmpty()
    ) return -1
    val digits = mapOf(
        "M" to 1000, "CM" to 900, "D" to 500, "CD" to 400, "C" to 100,
        "XC" to 90, "L" to 50, "XL" to 40, "X" to 10, "IX" to 9, "V" to 5, "IV" to 4, "I" to 1
    )
    var res = 0
    var i = 0
    while (i < roman.length) {
        if (i == roman.lastIndex) return res + digits[roman.substring(i, i + 1)]!!
        val s = roman.substring(i, i + 2)
        if (s in digits) {
            res += digits[s]!!
            i++
        } else res += digits[s.substring(0, 1)]!!
        i++
    }
    return res
}

/*
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    if (!commands.matches(Regex("""[<>+\-\[\] ]*"""))) throw IllegalArgumentException()
    val firstToLast = mutableMapOf<Int, Int>()
    val lastToFirst = mutableMapOf<Int, Int>()
    val indOfBraket = mutableListOf<Int>()
    for (i in 0 until commands.length) {
        when (commands[i]) {
            '[' -> indOfBraket.add(i)
            ']' -> {
                if (indOfBraket.isEmpty()) throw IllegalArgumentException()
                firstToLast[indOfBraket.last()] = i
                lastToFirst[i] = indOfBraket.last()
                indOfBraket.removeAt(indOfBraket.lastIndex)
            }
        }
    }
    if (indOfBraket.isNotEmpty()) throw IllegalArgumentException()
    val cellValue = MutableList(cells) { 0 }
    var j = cells / 2
    var commNum = 0
    var ind = 0
    while (ind < commands.length) {
        if (commNum == limit) break
        when (commands[ind]) {
            '>' -> {
                j++
                if (j == cells) throw IllegalStateException()
            }
            '<' -> {
                j--
                if (j == -1) throw IllegalStateException()
            }
            '+' -> cellValue[j]++
            '-' -> cellValue[j]--
            '[' -> if (cellValue[j] == 0) ind = firstToLast[ind]!!
            ']' -> if (cellValue[j] != 0) ind = lastToFirst[ind]!!
        }
        commNum++
        ind++
    }
    return cellValue
}
