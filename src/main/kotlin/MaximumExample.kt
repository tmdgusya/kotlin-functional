import java.lang.Error

fun main() {
    val given = listOf(1,5,3,7,8,4)
    val result = maximumWithSize(given, cur = 0, given.first())

    assert(result == 8)
    println(result)
}

fun maximum(list: List<Int>): Int = when {
    list.isEmpty() -> throw Error("Error")
    list.size == 1 -> list[0]
    else -> {
        val cur = list.first()
        val remain = list.subList(1, list.size)
        val max = maximum(remain)
        if (cur > max) cur else max
    }
}

fun maximumWithSize(list: List<Int>, cur: Int, maxVal: Int): Int = when {
    list.isEmpty() -> throw Error("Error")
    cur == list.size -> maxVal
    else -> {
        val max = if (list[cur] > maxVal) list[cur] else maxVal
        maximumWithSize(list, cur + 1, max)
    }
}