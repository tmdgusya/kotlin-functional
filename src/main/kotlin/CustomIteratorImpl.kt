class CustomIteratorImpl(
    private var result: Int = 0,
): Iterable<Int>, Iterator<Int> {

    override fun iterator(): Iterator<Int> {
        return this
    }

    override fun hasNext(): Boolean {
        return result < 3
    }

    override fun next(): Int {
        if (!hasNext()) throw NoSuchElementException()
        return result++
    }
}

fun <T> Iterator<T>.customFilter(
    predicate: (T) -> Boolean
): List<T> {
    val mutableList = mutableListOf<T>()
    for (ele in this) {
        if (predicate(ele)) mutableList.add(ele)
    }
    return mutableList.toList()
}

fun <T, R> Iterator<T>.map(
    transform: (T) -> R,
): List<R> {
    val list = mutableListOf<R>()
    for (ele in this) {
        list.add(transform(ele))
    }
    return list.toList()
}



fun main() {
    val iter: Iterator<Int> = CustomIteratorImpl()

    for (i in iter) {
        println(i)
    }

    val iter2 = CustomIteratorImpl()
    val iter3 = CustomIteratorImpl()

    val test = iter2.map { it * 2 }
    println(test)
    val test2 = iter3.customFilter { it % 2 == 1 }
    println(test2)
}