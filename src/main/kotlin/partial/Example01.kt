package partial

import java.lang.IllegalArgumentException

class PartialFunction<in P, out R>(
    private val condition: (P) -> Boolean,
    private val func: (P) -> R,
): (P) -> R {
    override fun invoke(p1: P): R = when {
        condition(p1)-> func(p1)
        else -> throw IllegalArgumentException("$p1 is not supported.")
    }

    fun isDefinedAt(p: P): Boolean = condition(p)

    fun invokeOrElse(p: P, default: @UnsafeVariance R): R = when {
        condition(p) -> func(p)
        else -> default
    }
}

val condition: (Int) -> Boolean = { it in 1..3 }
val body: (Int) -> String = {
    when (it) {
        1 -> "One"
        2 -> "Two"
        3 -> "Three"
        else -> throw IllegalArgumentException()
    }
}

fun main() {
    val oneTwoThree = PartialFunction(condition, body)
    if (oneTwoThree.isDefinedAt(3)) {
        println(oneTwoThree(3))
    } else {
        println("isDefinedAt(x) return false")
    }
}