@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    val result = MatrixImpl<E>(height, width)
    for (i in 0 until height)
        for (j in 0 until width)
            result[i, j] = e
    return result
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int) : Matrix<E> {

    private val list = MutableList<E?>(height * width) { null }

    override fun get(row: Int, column: Int): E = list[row * width + column] ?: throw IndexOutOfBoundsException()

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        list[row * width + column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun toString(): String = buildString {
        append('[')
        for (i in 0 until height) append("${list.subList(i * width, (i + 1) * width)}, ")
        delete(length - 2, length)
        append(']')
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatrixImpl<*>

        if (height != other.height) return false
        if (width != other.width) return false
        if (list != other.list) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + list.hashCode()
        return result
    }
}

