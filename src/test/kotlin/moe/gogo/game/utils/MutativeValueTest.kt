package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.test.generatePoint

class MutativeValueTest : StringSpec() {

    init {
        "equals"{
            val p1 = generatePoint()
            val p2 = generatePoint()
            val m1: MutativeValue<Point> = MutativeValue(p1, p2)
            val m2: MutativeValue<Any> = MutativeValue(Point(p1.x, p1.y), p2)
            m1 shouldBe m2
            m1.hashCode() shouldBe m2.hashCode()
            m1.toString() shouldBe m2.toString()
        }
    }

}