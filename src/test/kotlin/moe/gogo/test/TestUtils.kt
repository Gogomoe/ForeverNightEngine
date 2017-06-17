package moe.gogo.test

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.shouldBe
import moe.gogo.game.utils.equal

fun <E> `in`(collection: Collection<E>) = object : Matcher<E> {
    override fun test(value: E) = Result(value in collection, " $value should in the collection $collection")
}

fun equal(target: Float) = object : Matcher<Float> {
    override fun test(value: Float) = Result(target equal value, "$value should equal $target")
}

class TestStatus {
    var value: Int = 0
    fun next(step: Int = 1) {
        value += step
    }

    infix fun shouldBe(value: Int) = this.value shouldBe value
}

