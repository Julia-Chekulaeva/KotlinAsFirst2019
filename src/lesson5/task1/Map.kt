@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import javafx.scene.text.FontWeight
import kotlinx.html.I
import kotlin.math.max

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val grades2 = mutableMapOf<Int, MutableList<String>>()
    for ((student, mark) in grades) {
        if (grades2[mark] == null) grades2[mark] = mutableListOf()
        (grades2[mark]!!).add(student)
    }
    return grades2
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((c, d) in a) {
        if (d != b[c]) return false
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((c, d) in b) {
        if (a[c] == d) a.remove(c)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = (a.toSet().intersect(b.toSet())).toList()

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val res = mapA.toMutableMap()
    for ((name, num) in mapB) {
        if (name in res) {
            if (res[name] != num) res[name] += ", $num"
        } else res[name] = num
    }
    return res
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val sum = mutableMapOf<String, Double>()
    val count = mutableMapOf<String, Double>()
    val res = mutableMapOf<String, Double>()
    for ((action, price) in stockPrices) {
        sum[action] = (sum[action] ?: 0.0) + price
        count[action] = (count[action] ?: 0.0) + 1.0
    }
    for ((action, sumprice) in sum) res[action] = sumprice / count[action]!!
    return res
}


/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minCost = Double.POSITIVE_INFINITY
    var res: String? = null
    for ((name, pair) in stuff) {
        if (pair.first == kind && pair.second <= minCost) {
            minCost = pair.second
            res = name
        }
    }
    return res
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val c = chars.map { it.toLowerCase() }
    val w = word.toLowerCase().toSet() - c
    return w.isEmpty()
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    for (i in 0 until list.size) res[list[i]] = (res[list[i]] ?: 0) + 1
    return res.filter { it.value != 1 }.toMap()
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val countLetters = mutableSetOf<Map<Char, Int>>()
    val c = mutableMapOf<Char, Int>()
    for (word in words) {
        c.clear()
        for (i in 0 until word.length) c[word[i]] = (c[word[i]] ?: 0) + 1
        countLetters.add(c.toMap())
    }
    return countLetters.size != words.size
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */

fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val res = friends.toMutableMap()
    val friendsToAdd = mutableSetOf<String>()
    val friendsForLoop = mutableSetOf<String>()
    val namesToAdd = mutableListOf<String>()
    val finalFriends = mutableSetOf<String>()
    for ((key, value) in friends) {
        finalFriends.addAll(value)
        friendsForLoop.addAll(value)
        while (friendsForLoop.isNotEmpty()) {
            for (name2 in friendsForLoop) {
                friendsToAdd.addAll(friends[name2] ?: mutableSetOf())
                if (!friends.containsKey(name2) && !namesToAdd.contains(name2)) namesToAdd.add(name2)
            }
            friendsForLoop.addAll(friendsToAdd)
            friendsForLoop.removeAll(finalFriends)
            finalFriends.addAll(friendsToAdd)
            friendsToAdd.clear()
        }
        finalFriends.remove(key)
        res[key] = finalFriends.toSet()
        finalFriends.clear()
    }
    for (name in namesToAdd) res[name] = setOf()
    return res.toMap()
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val mapOfInd = mutableMapOf<Int, Int>()
    for (i in 0 until list.size) mapOfInd[list[i]] = i
    for (i in 0 until list.size) {
        if (mapOfInd.containsKey(number - list[i]) && i != mapOfInd[number - list[i]])
            return if (i < mapOfInd[number - list[i]]!!) Pair(i, mapOfInd[number - list[i]]!!)
            else Pair(mapOfInd[number - list[i]]!!, i)
    }
    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
/*val listOfNames = mutableListOf<String>()
var count = 0

fun maxSum(list: List<Triple<Int, Int, String>>, c: Int): Triple<Int, Int, List<String>> {
    count += 1
    println("$count:  $c $list $listOfNames   ${list.lastIndex}  ")
    if (list.isNotEmpty()) {
        if (list.last().first > c) {
            //print("out  ${list.last().third}")
            return maxSum(list - list.last(), c)
        }
        val m = maxSum(list - list.last(), c)
        val m2 = maxSum(list - list.last(), c - list.last().first)
        if (m2.second + list.last().second > m.second) {
            //print("include  ${list.last().third}")
            //if (a) listOfNames.add(list.last().third)
            //val res = m2//maxSum(list - list.last(), c - list.last().first, a)
            return Triple(
                m2.first + list.last().first,
                m2.second + list.last().second,
                m2.third + list.last().third
            )
        }
        //if (a) print("notInc  ${list.last().third}")
        return m//maxSum(list - list.last(), c, a)
    }
    return Triple(0, 0, listOf())
}

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    if (treasures.isEmpty()) return setOf()
    val s = mutableListOf<Triple<Int, Int, String>>()
    for ((key, value) in treasures) {
        s.add(Triple(value.first, value.second, key))
    }
    val h = maxSum(s.toList(), capacity)
    val res = mutableSetOf<String>()
    for (elem in h.third) {
        res.add(elem)
    }
    //val res = listOfNames.toSet()
    //print("-  $h $res  end   ")
    listOfNames.clear()
    return res
}

var w = 0
var maxPrice = 0
val listOfNames = mutableSetOf<String>()


fun allVar(price: List<Int>, weight: List<Int>, names: List<String>, i: Int, sumWeight: Int, sumPrice: Int): Unit {
    //println("$i $sumWeight $sumPrice ${names[i]} ${price[i]} ${weight[i]}")
    if (sumWeight <= w && sumPrice > maxPrice) {
        maxPrice = sumPrice
        listOfNames.clear()
        listOfNames.addAll(names)
    }
    if (i == 0) {
        if (sumWeight - weight[i] <= w && sumPrice - price[i] > maxPrice) {
            maxPrice = sumPrice - price[i]
            listOfNames.clear()
            listOfNames.addAll(names - names[i])
        }
    } else {
        allVar(price, weight, names, i - 1, sumWeight, sumPrice)
        val size = price.size
        if (i < size - 1) allVar(
                price.subList(0, i) + price.subList(i + 1, size),
                weight.subList(0, i) + weight.subList(i + 1, size),
                names.subList(0, i) + names.subList(i + 1, size),
                i - 1, sumWeight - weight[i], sumPrice - price[i]
            )
        else allVar(
            price.subList(0, i),
            weight.subList(0, i),
            names.subList(0, i),
            i - 1, sumWeight - weight[i], sumPrice - price[i]
        )
    }
}


fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    if (treasures.isEmpty()) return setOf<String>()
    val Price = mutableListOf<Int>()
    val Weight = mutableListOf<Int>()
    val Names = mutableListOf<String>()
    for ((key, value) in treasures) {
        Price.add(value.second)
        Weight.add(value.first)
        Names.add(key)
    }
    listOfNames.clear()
    w = capacity
    maxPrice = 0
    println(Price.size)
    allVar(Price, Weight, Names, Price.size - 1, Weight.sum(), Price.sum())
    return listOfNames.toSet()
}*/

/*fun main() {
    println(bagPacking(mapOf("0" to (1 to 1), "1" to (1 to 2)), 2))
    println(bagPacking(mapOf("0" to (1 to 1), "1" to (1 to 2), "2" to (1 to 1)), 2))
}*/

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val t: Map<String, Pair<Int, Int>> = treasures.filter { it.value.first <= capacity }.toMap()
    val num = t.size
    val names = arrayOf<MutableList<Set<String>>>(mutableListOf(), mutableListOf())
    for (j in 0..1)
        for (i in 0..capacity) names[j].add(setOf())
    val name = mutableListOf<String>()
    val prices = mutableListOf<Int>()
    val weights = mutableListOf<Int>()
    for ((key, value) in t) {
        name.add(key)
        weights.add(value.first)
        prices.add(value.second)
    }
    val maxPrice = mutableListOf<MutableList<Int>>()
    for (i in 0..num) maxPrice.add(mutableListOf<Int>())
    for (i in 0..capacity) maxPrice[0].add(0)
    for (i in 1..num) {
        //println("Код работает!!")
        for (j in 0..capacity) {
            //println("  $j")
            if (weights[i - 1] > j)
                maxPrice[i].add(maxPrice[i - 1][j])
            else if (maxPrice[i - 1][j] >= maxPrice[i - 1][j - weights[i - 1]] + prices[i - 1])
                maxPrice[i].add(maxPrice[i - 1][j])
            else {
                maxPrice[i].add(maxPrice[i - 1][j - weights[i - 1]] + prices[i - 1])
                names[1][j] = names[0][j - weights[i - 1]] + name[i - 1]
            }
            /*println("$i. ${names[1]}")
            println("$maxPrice")
            println()*/
        }
        for (s in 0..capacity) names[0][s] = names[1][s]
    }
    return names[1][capacity]
}