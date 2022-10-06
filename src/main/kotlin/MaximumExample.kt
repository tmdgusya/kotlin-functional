import java.lang.Error

fun <T> List<T>.tail(): List<T> = this.drop(1)
fun <T> List<T>.head(): T = this.first()

fun main() {
    val given = listOf(1,5,3,7,8,4)
    val result = maximum(given)

    assert(result == 8)
    println(result)
}

fun maximum(list: List<Int>): Int = when {
    list.isEmpty() -> throw Error("Error")
    list.size == 1 -> list[0]
    else -> {
        val cur = list.head()
        val remain = list.tail()
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