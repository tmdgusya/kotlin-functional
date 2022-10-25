fun range(l: Int): Iterator<Int> {
    var i = -1
    val result = mutableListOf<Int>()
    while (++i < l) {
        println("produce $i")
        result.add(i)
    }
    return result.toList().iterator()
}

suspend fun lazyRange(l: Int) = sequence {
    var i = -1
    while (++i < l) {
        println("produce $i")
        yield(i)
    }
}

val add = { a: Int, b: Int -> a + b }

suspend fun main() {
//    println(range(5).reduce(add, 0))
    println(lazyRange(5).iterator().reduce(add, 0))
}