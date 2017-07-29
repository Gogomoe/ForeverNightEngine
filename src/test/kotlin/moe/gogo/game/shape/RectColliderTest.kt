package moe.gogo.game.shape

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldNot
import io.kotlintest.specs.StringSpec

class RectColliderTest : StringSpec() {

    init {
        "contact other rect"{
            val r1 = createRect(20, 20, 0, 0)
            val r2 = createRect(5, 20, 0, 15)
            val r3 = createRect(15, 15, 15, 15)
            val r4 = createRect(20, 5, 15, 0)
            val r5 = createRect(50, 50, 0, 0)

            r1 should contact(r2)
            r1 should contact(r3)
            r1 should contact(r4)
            r1 should contact(r5)
            r2 should contact(r1)
            r3 should contact(r1)
            r4 should contact(r1)
            r5 should contact(r1)

            r2 shouldNot contact(r3)
            r2 shouldNot contact(r4)
            r3 shouldNot contact(r2)
            r3 shouldNot contact(r4)
            r4 shouldNot contact(r2)
            r4 shouldNot contact(r3)
        }
    }

}

private val collider = RectCollider()

fun contact(target: Rect) = object : Matcher<Rect> {
    override fun test(value: Rect) = Result(collider.contact(target, value), "$value should contact $target")
}
