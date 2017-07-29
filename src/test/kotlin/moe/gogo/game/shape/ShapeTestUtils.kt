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

fun createRect(w: Int, h: Int, x: Number = 0, y: Number = 0) = Rect(w, h, Point(x, y))

fun createCircle(r: Int, x: Number = 0, y: Number = 0) = Circle(r, Point(x, y))

class ShapeA(val rect: Rect) : Shape() {

    override fun contains(point: Point): Boolean = rect.contains(point)

    override fun boundingRect(): Rect = rect

    override fun shiftTo(point: Point): Shape = rect.shiftTo(point)

}

class ShapeB(val rect: Rect) : Shape() {

    override fun contains(point: Point): Boolean = rect.contains(point)

    override fun boundingRect(): Rect = rect

    override fun shiftTo(point: Point): Shape = rect.shiftTo(point)

}

class ABCollider : Collider<ShapeA, ShapeB>() {
    override fun contact(t: ShapeA, s: ShapeB): Boolean = RectCollider.contact(t.rect, s.rect)

}

class BACollider : Collider<ShapeA, ShapeB>() {
    override fun contact(t: ShapeA, s: ShapeB): Boolean = RectCollider.contact(t.rect, s.rect)

}