package moe.gogo.game.shape

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldNot
import io.kotlintest.specs.StringSpec
import moe.gogo.game.utils.Point

class CircleColliderTest : StringSpec() {

    init {
        "circle contact"{
            createCircle(5, 0, 0) should contact(createCircle(1, 0, 2))
            createCircle(5, 0, 0) should contact(createCircle(5, 5, 5))
            createCircle(5, 0, 0) should contact(createCircle(10, -5, 6))
            createCircle(5, 0, 0) shouldNot contact(createCircle(2, -5, -8))
            createCircle(5, 0, 0) shouldNot contact(createCircle(7, 23, 67))
        }
    }

}