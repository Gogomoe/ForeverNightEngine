package moe.gogo.test

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.shouldBe

fun <E> `in`(collection: Collection<E>) = object : Matcher<E> {
    override fun test(value: E) = Result(value in collection, " $value should in the collection $collection")
}

class TestStatus {
    var value: Int = 0
    fun next(step: Int = 1) {
        value += step
    }

    infix fun shouldBe(value: Int) = this.value shouldBe value
}