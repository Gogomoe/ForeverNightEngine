package moe.gogo.test

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.shouldBe
import moe.gogo.game.utils.equal
import java.awt.GraphicsEnvironment

fun <E> `in`(collection: Collection<E>) = object : Matcher<E> {
    override fun test(value: E) = Result(value in collection, "$value should in the collection $collection")
}

fun bigger(number: Number) = object : Matcher<Number> {
    override fun test(value: Number) = Result(value.toDouble() >= number.toDouble(), "$value should bigger than $number")
}

fun equal(target: Float) = object : Matcher<Float> {
    override fun test(value: Float) = Result(target equal value, "$value should equal $target")
}

fun equal(target: Number) = object : Matcher<Number> {
    override fun test(value: Number) = Result(target equal value, "$value should equal $target")
}

class TestStatus {
    var value: Int = 0
    fun next(step: Int = 1) {
        value += step
    }

    infix fun shouldBe(value: Int) = this.value shouldBe value

    infix fun shouldBigger(value: Int) = this.value shouldBe bigger(value)
}

fun couldRunSwing(): Boolean = !GraphicsEnvironment.isHeadless()