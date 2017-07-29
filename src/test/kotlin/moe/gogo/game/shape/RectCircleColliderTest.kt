package moe.gogo.game.shape

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldNot
import io.kotlintest.specs.StringSpec

class RectCircleColliderTest : StringSpec() {

    init {
        "rect circle collider"{
            createRect(5, 4) should collider(createCircle(1))
            createRect(5, 4) should collider(createCircle(10))
            createRect(5, 5) should collider(createCircle(5, 10, 0))
            createRect(5, 5) should collider(createCircle(5, 0, -10))
            createRect(5, 5) should collider(createCircle(5, 8, 8))

            createRect(5, 5) shouldNot collider(createCircle(5, 10, 10))
            createRect(5, 5) shouldNot collider(createCircle(5, 11, 0))
            createRect(5, 5) shouldNot collider(createCircle(5, -6, -10))
        }
    }


    fun collider(target: Circle) = object : Matcher<Rect> {
        override fun test(value: Rect) = Result(value.contact(target) && target.contact(value), "$value should collider $target")
    }

}