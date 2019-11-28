@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import lesson4.task1.abs
import java.lang.IllegalArgumentException
import kotlin.math.max
import kotlin.math.min
import lesson8.task3.Graph

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String = if (inside()) ('a' + column - 1).toString() + "$row"
    else ""

    fun cornerSquare(): Boolean = (column == 1 || column == 8) && (row == 1 || row == 8)
}

fun toSquare(s: String) = Square(s[0] - 'a' + 1, s[1] - '0')

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square = if (notation.matches(Regex("""[a-h][1-8]"""))) Square(
    notation[0] - 'a' + 1, notation[1] - '0'
) else throw IllegalArgumentException()

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int = if (start.inside() && end.inside()) when {
    start == end -> 0
    start.row == end.row || start.column == end.column -> 1
    else -> 2
} else throw IllegalArgumentException()

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> = when (rookMoveNumber(start, end)) {
    0 -> listOf(start)
    1 -> listOf(start, end)
    2 -> listOf(start, Square(start.column, end.row), end)
    else -> throw IllegalArgumentException()
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun rowDiff(start: Square, end: Square) = kotlin.math.abs(start.row - end.row)

fun columnDiff(start: Square, end: Square) = kotlin.math.abs(start.column - end.column)

fun bishopMoveNumber(start: Square, end: Square): Int {
    val column = columnDiff(start, end)
    val row = rowDiff(start, end)
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    return when {
        start == end -> 0
        column == row -> 1
        column % 2 == row % 2 -> 2
        else -> -1
    }
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    when (bishopMoveNumber(start, end)) {
        0 -> return listOf(start)
        1 -> return listOf(start, end)
        2 -> {
            val row = rowDiff(start, end)
            val column = columnDiff(start, end)
            val average = (column + row) / 2
            val columnJump = if (start.column < end.column) average else -average
            val rowJump = if (start.row < end.row) average else -average
            val middleSquare = Square(start.column + columnJump, start.row + rowJump)
            return if (middleSquare.inside()) listOf(start, middleSquare, end)
            else listOf(start, Square(end.column - columnJump, end.row - rowJump), end)
        }
        else -> return listOf<Square>()
    }
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    return max(columnDiff(start, end), rowDiff(start, end))
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val way = mutableListOf<Square>()
    val startRow = start.row
    val startColumn = start.column
    val turnPoint = min(columnDiff(start, end), rowDiff(start, end))
    val endPoint = kingMoveNumber(start, end)
    val rowDirest = if (startRow < end.row) 1 else -1
    val columnDirest = if (startColumn < end.column) 1 else -1
    for (i in 0 until turnPoint)
        way.add(Square(startColumn + i * columnDirest, startRow + i * rowDirest))
    val turnColumn = startColumn + turnPoint * columnDirest
    if (turnColumn == end.column) for (i in turnPoint..endPoint)
        way.add(Square(turnColumn, startRow + i * rowDirest))
    else {
        val turnRow = startRow + turnPoint * rowDirest
        for (i in turnPoint..endPoint) way.add(Square(startColumn + i * columnDirest, turnRow))
    }
    return way
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    val rowDiff = rowDiff(start, end)
    val columnDiff = columnDiff(start, end)
    if (rowDiff > 4 || columnDiff > 4) {
        val rowDirect = if (start.row < end.row || end.row == 8) 1 else -1
        val columnDirect = if (start.column < end.column || end.column == 8) 1 else -1
        val square1 = Square(end.column - columnDirect, end.row - rowDirect * 2)
        val square2 = Square(end.column - columnDirect * 2, end.row - rowDirect)
        return when {
            !square1.inside() -> 1 + knightMoveNumber(start, square2)
            !square2.inside() -> 1 + knightMoveNumber(start, square1)
            else -> 1 + min(knightMoveNumber(start, square1), knightMoveNumber(start, square2))
        }
    }
    return if (rowDiff % 2 == columnDiff % 2) {
        if (columnDiff == rowDiff && (columnDiff % 2 == 0 || (columnDiff == 1 &&
                    (start.cornerSquare() || end.cornerSquare())))
        ) {
            if (start == end) 0 else 4
        } else 2
    } else if ((columnDiff == 1 && rowDiff == 2) || (columnDiff == 2 && rowDiff == 1)) 1
    else 3
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */

fun knightTrajectory(start: Square, end: Square): List<Square> {
    TODO()
    val g = Graph()
    val list = listOf(1 to 2, 1 to -2, 2 to 1, 2 to -1)
    for (i in 1..8) {
        for (j in 1..8) {
            val top1 = Square(i, j).notation()
            g.addVertex(top1)
            for ((c, r) in list) {
                val s = Square(i + c, j + r)
                if (!s.inside()) continue
                val top2 = s.notation()
                g.addVertex(top2)
                g.connect(top1, top2)
            }
        }
    }
    //println(102132435465)
    return g.minWay(start.notation(), end.notation()).map { toSquare(it) }
}