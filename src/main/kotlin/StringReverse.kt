fun String.head() = this.first()
fun String.tail() = this.drop(1)

fun main() {
    val given = "abcd"
    val result = reverse(given)
    val expected = "dcba"

    println(expected == result)
}

fun reverse(s: String): String = when {
    s.isEmpty() -> ""
    else -> reverse(s.tail()) + s.head()
}