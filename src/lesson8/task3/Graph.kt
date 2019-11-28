package lesson8.task3

import java.util.*

class Graph {
    private data class Vertex(val name: String) {
        val neighbors = mutableSetOf<Vertex>()
    }

    private val vertices = mutableMapOf<String, Vertex>()

    private operator fun get(name: String) = vertices[name] ?: throw IllegalArgumentException()

    fun addVertex(name: String) {
        vertices[name] = Vertex(name)
    }

    private fun connect(first: Vertex, second: Vertex) {
        first.neighbors.add(second)
        second.neighbors.add(first)
    }

    fun connect(first: String, second: String) = connect(this[first], this[second])

    fun minWay(first: String, second: String): List<String> {
        var next1 = first
        val res = mutableMapOf(next1 to listOf(next1))
        val used = mutableSetOf(next1 to vertices[next1]!!)
        val reverseVertices = vertices.map { it.value to it.key }.toMap()
        var used2 = used
        while (next1 != second) {
            println(res)
            val lastUsed = used2
            used.addAll(lastUsed)
            used2 = mutableSetOf()
            for (elem in lastUsed) {
                next1 = elem.first
                val top1 = vertices[next1]!!
                val usedVertex = used.map { it.second }
                for (top2 in top1.neighbors.filter { it !in usedVertex }) {
                    val next2 = reverseVertices[top2]!!
                    used2.add(next2 to top2)
                    res[next2] = res[next1]!! + next2
                    if (next2 == second) return res[next2]!!
                }
            }
        }
        return res[next1]!!
    }

    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в ширину
     */
    fun bfs(start: String, finish: String) = bfs(this[start], this[finish])

    private fun bfs(start: Vertex, finish: Vertex): Int {
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) return distance
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited[neighbor] = distance + 1
                queue.add(neighbor)
            }
        }
        return -1
    }

    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в глубину
     */
    fun dfs(start: String, finish: String): Int = dfs(this[start], this[finish], setOf()) ?: -1

    private fun dfs(start: Vertex, finish: Vertex, visited: Set<Vertex>): Int? =
        if (start == finish) 0
        else {
            val min = start.neighbors
                .filter { it !in visited }
                .mapNotNull { dfs(it, finish, visited + start) }
                .min()
            if (min == null) null else min + 1
        }
}
