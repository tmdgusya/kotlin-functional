fun main() {
    println(fiboRecursion(10, IntArray(100)))
}

fun fibo(n: Int, fibo: IntArray): Int {
    fibo[0] = 0
    fibo[1] = 1

    for (i in 2..n) {
        fibo[i] = fibo[i - 1] + fibo[i - 2]
    }

    return fibo[n]
}

fun fiboRecursion(n: Int, fibo: IntArray): Int {
    return when (n) {
        0 -> 0
        1 -> 1
        else -> fiboRecursion(n - 1, fibo) + fiboRecursion(n - 2, fibo)
    }
}