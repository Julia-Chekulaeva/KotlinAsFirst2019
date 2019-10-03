@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    if (n == 0) return 1
    var s = if (n > 0) n else -n
    var i = 0
    while (s > 0){
        i += 1
        s /= 10
    }
    return i
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int = when (n){
    1 -> 1
    2 -> 1
    else -> fib(n / 2 + 1) * fib(n - n / 2) + fib(n / 2) * fib(n - n / 2 - 1)
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var s = m
    while (s % n > 0)
        s += m
    return s
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */

fun minDivisor(n: Int): Int {
    var del = 2
    while (n % del > 0)
        del += 1
    return del
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var del = n - 1
    while (n % del > 0)
        del -= 1
    return del
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var s1 = m
    var s2 = n
    while (s1 != s2) {
        if (s1 > s2) s1 -= s2
        else s2 -= s1
    }
    return s1 == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean = (sqrt(n.toDouble()).toInt() * sqrt(n.toDouble()).toInt() >= m)

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var x1 = x
    var i = 0
    while (x1 > 1){
        i += 1
        if (x1 % 2 == 0) x1 = x1 / 2
        else x1 = x1 * 3 + 1
    }
    return i
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    var i = 1.0
    var b = x
    var modB = if (b >= 0.0) b else -b
    var sin = x
    while (modB >= eps){
        i = i + 2.0
        b = -b * x * x / (i * (i - 1.0))
        modB = if (b >= 0.0) b else -b
        sin = sin + b
    }
    return sin
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    var i = 0.0
    var a = 1.0
    var modA = if (a >= 0.0) a else -a
    var cos = 1.0
    while (modA >= eps){
        i = i + 2.0
        a = -a * x * x / (i * (i - 1.0))
        modA = if (a >= 0.0) a else -a
        cos = cos + a
    }
    return cos
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var num = n
    var rev = 0
    while (num > 0){
        rev = rev * 10 + num % 10
        num /= 10
    }
    return rev
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var n1 = n
    val digit = n % 10
    while (n1 > 0){
        if (n1 % 10 != digit) return true
        n1 /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var length = 0 // Длина строчки до какой-то "границы" чисел с разным размером (10, 100 и т.д. - границы)
    var i = 0 // Длина (размер) чисел
    var border = 1.0
    while (length < n) {
        border *= 10.0
        i += 1
        length += (sqrt(border - 1.0).toInt() - sqrt((border / 10) - 1.0).toInt()) * i
    }
    val num = sqrt(border - 1.0).toInt() - (length - n) / i // Ищу число, в квадрате которого искомая цифра
    val digitNumber = (length - n) % i // На каком она месте (начиная с низших разрядов)
    var squareN = num * num
    for (s in 1..digitNumber)
        squareN = squareN / 10
    return squareN % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var length = 0 // Та же длина строки, но теперь прямо до нужного числа
    var i = 0 // Та же длина числа
    var ind = 0 // Номер числа
    var num = 0 // Та же "граница"
    while (length < n){
        i = 0
        ind += 1
        num = fib(ind)
        while (num > 0){
            i += 1
            num /= 10
        }
        length += i
    }
    num = fib(ind)
    var k = 0
    while (k < length - n) {
        num = num / 10
        k += 1
    }
    return num % 10
}
