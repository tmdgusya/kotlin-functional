
fun <T> Iterator<T>.take(limit: Int): List<T> {
    val result = mutableListOf<T>()
    for (ele in this) {
        if (result.size == limit) return result
        result.add(ele)
    }
    return result
}

fun main() {
    println(listOf(1, 2, 3, 4, 5, 6).iterator().take(4))
    sequenceOf(1, 2, 3, 4, 5, 6).take(4)
}