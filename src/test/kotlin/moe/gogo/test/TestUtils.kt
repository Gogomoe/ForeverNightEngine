package moe.gogo.test

import com.google.common.collect.HashMultiset
import com.google.common.collect.Multiset
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

fun has(name: String, count: Int) = object : Matcher<TestStatus> {
    override fun test(value: TestStatus): Result = Result(value.count(name) >= count, "count $name should bigger than $value")
}

fun has(name: String) = object : Matcher<TestStatus> {
    override fun test(value: TestStatus): Result = Result(value.count(name) >= 1, "should has $name")
}

class TestStatus {

    val set: Multiset<String> = HashMultiset.create()

    var value: Int = 0
    fun next(step: Int = 1) {
        value += step
    }

    fun next(name: String, count: Int = 1) = set.add(name, count)

    fun count(name: String) = set.count(name)

    fun has(name: String, count: Int = 1) = set.count(name) >= count

    infix fun shouldBe(value: Int) = this.value shouldBe value

    infix fun shouldBigger(value: Int) = this.value shouldBe bigger(value)

    operator fun get(name: String) = set.count(name)
    operator fun set(name: String, value: Int) = set.setCount(name, value)
}

fun couldRunSwing(): Boolean = !GraphicsEnvironment.isHeadless()

inline fun runSwing(block: () -> Unit) {
    if (couldRunSwing()) {
        block()
    }
}