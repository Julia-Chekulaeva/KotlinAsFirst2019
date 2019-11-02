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
    val num: Int
    var month = 0
    val year: Int
    if (parts.size == 3) {
        try {
            num = parts[0].toInt()
            year = parts[2].toInt()
            for (i in 0..11) {
                if (parts[1] == months[i]) month = i + 1
            }
            if (num in 1..lesson2.task2.daysInMonth(month, year))
                return String.format("%02d.%02d.%d", num, month, year)
        } catch (e: NumberFormatException) {
            return ""
        }
    }
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
    val num: Int
    val month: Int
    val year: Int
    if (parts.size == 3) {
        try {
            num = parts[0].toInt()
            month = parts[1].toInt()
            year = parts[2].toInt()
            if (num in 1..lesson2.task2.daysInMonth(month, year))
                return "$num ${months[month - 1]} $year"
        } catch (e: NumberFormatException) {
            return ""
        }
    }
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
    var s = phone.filter { it != ' ' && it != '-' }
    if (s == "") return s
    val plus: String
    val i1: Int
    val i2: Int
    if (s[0] == '+') {
        plus = "+"
        s = s.substring(1)
    } else plus = ""
    if (s.isEmpty()) return ""
    var i = 0
    while (s[i] != '(' && i < s.length - 3) i += 1
    i1 = i
    if (s[i] == '(' && i + 2 < s.length) i += 2
    if (i != i1) while (s[i] != ')' && i < s.length - 1) i += 1
    i2 = if (s[i] == ')') i
    else i1
    if (i1 != i2) s = s.substring(0, i1) + s.substring(i1 + 1, i2) + s.substring(i2 + 1)
    if (s.isEmpty()) return ""
    for (j in 0 until s.length) {
        if (s[j] !in '0'..'9') {
            return ""
        }
    }
    return plus + s
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
    try {
        for (elem in attempts) {
            if (elem.toInt() > max) max = elem.toInt()
        }
    } catch (e: NumberFormatException) {
        return -1
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
    val s = jumps.split(" ")
    val high = mutableListOf<Int>()
    val attempts = mutableListOf<String>()
    if (s.size % 2 == 0) {
        try {
            for (i in 0 until s.size / 2) {
                high.add(s[2 * i].toInt())
                if (s[2 * i + 1].substring(0, s[2 * i + 1].lastIndex).filter { it != '%' } == "" &&
                    (s[2 * i + 1].last() == '%' || s[2 * i + 1].last() == '-' || s[2 * i + 1].last() == '+'))
                    attempts.add(s[2 * i + 1])
                else high.add("a".toInt())
            }
        } catch (e: NumberFormatException) {
            return -1
        }
        var max = -1
        for (i in 0 until s.size / 2) {
            if (attempts[i].last() == '+' && high[i] > max) max = high[i]
        }
        return max
    }
    return -1
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
    val counter = expression.split(" ")
    var sum: Int
    var c: Int
    if (counter.size % 2 == 1) {
        try {
            if (counter[0].isEmpty()) throw IllegalArgumentException()
            sum = if (counter[0][0] != '+' && counter[0][0] != '-') counter[0].toInt() else "a".toInt()
            for (i in 1..counter.size / 2) {
                if (counter[2 * i].isEmpty()) throw IllegalArgumentException()
                c = if (counter[2 * i][0] != '+' && counter[2 * i][0] != '-') counter[2 * i].toInt() else "a".toInt()
                when (counter[2 * i - 1]) {
                    "+" -> sum += c
                    "-" -> sum -= c
                    else -> throw IllegalArgumentException()
                }
            }
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException()
        }
        return sum
    }
    throw IllegalArgumentException()
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
    val things = mutableListOf<String>()
    var maxSum = 0.0
    var bestGood = ""
    try {
        for (elem in s) {
            things.addAll(elem.split(" "))
            if (things.size == 2) {
                if (things[1].toDouble() >= maxSum) {
                    maxSum = things[1].toDouble()
                    bestGood = things[0]
                }
            } else return ""
            things.clear()
        }
    } catch (e: java.lang.NumberFormatException) {
        return ""
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
    if (roman == "") return -1
    val digits = listOf(
        listOf('M' to 1000, 'M' to 1000),
        listOf('D' to 500, 'C' to 100),
        listOf('L' to 50, 'X' to 10),
        listOf('V' to 5, 'I' to 1)
    )
    var j = 0
    var res = 0
    var canUse = true
    for (i in 0..2) {
        if (roman[i] == digits[0][0].first) {
            res += digits[0][0].second
            j++
            if (j == roman.length) return res
        } else break
    }
    for (i in 1..3) {
        if (roman[j] == digits[i][0].first) {
            res += digits[i][0].second
            j++
            if (j == roman.length) return res
            canUse = false
        }
        if (roman[j] == digits[i][1].first) {
            if (j == roman.lastIndex) return res + digits[i][1].second
            when {
                roman[j + 1] == digits[i][0].first && canUse -> {
                    res += digits[i][0].second - digits[i][1].second
                    j += 2
                }
                roman[j + 1] == digits[i - 1][1].first && canUse -> {
                    res += digits[i - 1][1].second - digits[i][1].second
                    j += 2
                }
                else -> {
                    for (a in 0..2) {
                        if (roman[j] == digits[i][1].first) {
                            res += digits[i][1].second
                            j++
                            if (j == roman.length) return res
                        } else break
                    }
                }
            }
        }
        canUse = true
    }
    if (j < roman.length) return -1
    return res
}

/**
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
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
