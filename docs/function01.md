# Kotlin Sequence

Kotlin 에는 Collection 과 함께 `Sequence` 라는 스펙이 존재한다. 공식문서에 따르면 Collection 처럼 
Element 를 저장하는 방식이 아니고, `Iterator` 에 가깝다. 정확히는 **순회하면서 Element 를 생산(Produce)** 
한다.  

## Lazily Evaluation

Sequence 의 가장 큰 장점은 지연 평가가 가능하다는 것이다. 즉, 우리가 Collection 을 사용하여 **map, filter, ...** 와 같은 연산을 수행하게 될 경우 
그 즉시 값을 계산하게 된다. 예를 들면 `listOf(1,2,3,4,5).map {it * 2}` 와 같이 될 경우 5개 Element 를 그 즉시 계산하게 된다는 것이다. 당연하게도 
위와 같이 **즉시** 연산이 필요한 경우도 많다.  

근데 만약, 무한하게 값이 들어올 수 있는 부분에서 `map` 연산을 사용해야 한다면 어떻게 해야할까? 예를 들면 아래와 같은 상황에서 말이다.
`listOf(1,2,3,...).map { it * 2 }` 이렇게 되면 무한하게 들어오는 Element 들을 모두 메모리에 올려야 하는 상황이 연출될 것이다. 
가능할까? 당연히 불가능 할 것이다. 이런 **무한한 특성을 가진 자료구조를 처리하는데 적합한 것이 바로 Kotlin 의 Sequence Spec** 이다.

### List 예시

예시 코드로 한번 테스트 해보자.

```kotlin
fun <T> Iterator<T>.reduce(
    acc: (T, T) -> T,
    start: T
): T {
    var temporal: T = start
    for (ele in this) {
        println("eval: $ele")
        temporal = acc(temporal, ele)
    }
    return temporal
}
```

위 코드는 custom 한 `reduce` 함수인데, 그냥 간단하게 acc 함수를 넣으면 element 를 넣어서 순회면서 연산한 값을 리턴해주는 함수이다. 
Iterator 의 확장함수로 아래처럼 List 를 만든뒤 활용 가능하다.

```kotlin
fun range(l: Int): Iterator<Int> {
    var i = -1
    val result = mutableListOf<Int>()
    while (++i < l) {
        println("produce $i")
        result.add(i)
    }
    return result.toList().iterator()
}

val add = { a: Int, b: Int -> a + b }

fun main() {
    println(range(5).reduce(add, 0))
}
```

실행시켜보면 아래와 같은 출력결과가 나오는 것을 확인할 수 있다.

```shell
produce 0
produce 1
produce 2
produce 3
produce 4
eval: 0
eval: 1
eval: 2
eval: 3
eval: 4
10
```

먼저 Iterator 에 대한 생산(produce) 가 먼져 이뤄진 후, 그 이후에 값이 평가됨을 확인할 수 있다.

동일한 동작을 sequence 로 바꿔보면 어떨까? 

### Sequence 

```kotlin
suspend fun lazyRange(l: Int) = sequence {
    var i = -1
    while (++i < l) {
        println("produce $i")
        yield(i)
    }
}

val add = { a: Int, b: Int -> a + b }

suspend fun main() {
    println(lazyRange(5).iterator().reduce(add, 0))
}
```

동일한 코드를 **sequence** 로 바꿔보면 위와 같이 변한다. Intellij 에서 위와 같이 코드를 작성하면 suspend point 를 찍어주는 것을 확인할 수 있다. 
뒤에서 설명하겠지만, sequence 는 suspend function 으로 `일시 중단 - 재개` 기반으로 동작한다. 일단 위 코드의 결과를 한번 보자.

```kotlin
produce 0
eval: 0
produce 1
eval: 1
produce 2
eval: 2
produce 3
eval: 3
produce 4
eval: 4
10
```

결과를 보면 **평가되는 시점에 값을 하나씩 생산해서 사용**하는 것을 확인할 수 있다. 즉, 필요하지 않다면 값을 만들지 않는 다는 것이다. 
따라서, 특정 상황에서는 Memory 적으로 Sequence 가 이득일 수 있다.

### 어떻게 이렇게 동작하지?

위에서 힌트를 준것 같지만 `suspend mechanism` 을 이용하는 것이다. 
즉, 평가가 필요한 시점에 while 문을 한번 돌려서 값을 생산하고 `SUSPEND` 시킨다. 
이후에 다시 값이 필요하게 되었을때는 SUSPEND 된 부분으로 부터 while 문을 한번 더 수행하여 값을 생산한다. 이와 같은 메커니즘으로 동작하기에 가능한 것이다.

### 생각해볼점

항상 Collections 를 사용할 필요는 없다. 코틀린에서는 Sequence 를 쉽게 쓸 수 있도록 map, reduce 등등의 연산자들도 제공하므로 
지연평가가 필요한 부분에 잘 적용해보도록 하자.