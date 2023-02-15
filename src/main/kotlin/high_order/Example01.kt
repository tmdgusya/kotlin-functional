package high_order

fun highOrder(func: (Int, Int) -> Int, x: Int, y: Int) = func(x, y)

fun main() {
    val twiceSum: (Int, Int) -> Int = { x, y -> (x + y) * 2 }
    println(highOrder(twiceSum, 8, 2))
}