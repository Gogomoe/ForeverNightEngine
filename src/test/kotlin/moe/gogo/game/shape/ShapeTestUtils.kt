package moe.gogo.game.shape

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import moe.gogo.game.utils.Point


fun contains(target: Point) = object : Matcher<Shape> {
    override fun test(value: Shape) = Result(value.contains(target), "$value should contains $target")
}

fun contact(target: Shape) = object : Matcher<Shape> {
    override fun test(value: Shape) = Result(value.contact(target), "$value should contact $target")
}