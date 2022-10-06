fun main() {
    println(recursiveSum(5))
}

fun recursiveSum(limit: Int): Int {
    return when (limit) {
        0 -> 0 // 종료 조건
        else -> limit + recursiveSum(limit - 1)
    }
}