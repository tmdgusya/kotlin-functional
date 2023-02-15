package partial

import kotlin.math.abs

val absolute = { i: List<Int> -> i.map { abs(it) } }
val negative = {i: List<Int> -> i.map { it * -1 } }
val minimum = {i: List<Int> -> i.min() }

fun main() {
    val result = minimum(negative(absolute(listOf(1, 2, 3, 4, 5))))
    println(result)

    val result2 = square(max(listOf(1, 2, 3, 4, 5)))
    println(result2)

    val composed = square compose max
    val result3 = composed(listOf(1,2,3,4,5))
    println(result3)
}

val square = { i: Int -> i * i }
val max = { list: List<Int> -> list.max() }

infix fun <P, Q, R> ((P) -> R).compose(func: (Q) -> P): (Q) -> R {
    return { x: Q -> this(func(x)) }
}