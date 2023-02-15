package partial

fun main() {
    val result = multiThree(1)(2)(3)
    assert(result == 6)

    val curried = { x: Int, y: Int, z: Int -> x * y * z }.curried()
    println(curried(1)(2)(3))

    val uncurried = curried.uncurried<Int, Int, Int, Int>()
    println(uncurried(1, 2, 3))
}

fun multiThree(a: Int) = { b: Int -> { c: Int -> a * b * c } }

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).curried(): (P1) -> (P2) -> (P3) -> R =
    { x: P1 -> { y: P2 -> { z: P3 -> this(x, y, z) } } }

fun <P1, P2, P3, R> ((P1) -> (P2) -> (P3) -> R).uncurried() = { x: P1, y: P2, z: P3 -> this(x)(y)(z) }