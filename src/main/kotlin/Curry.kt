val curriedAdd: (Int) -> (Int) -> Int = { x -> { y -> x + y } }

fun main() {
    val result = curriedAdd(3)(5)
}