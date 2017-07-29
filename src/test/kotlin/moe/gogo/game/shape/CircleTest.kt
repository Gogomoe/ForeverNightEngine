package moe.gogo.game.shape

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNot
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.game.utils.Point
import moe.gogo.test.generateXY

class CircleTest : StringSpec() {

    init {
        "position"{
            val (x, y) = generateXY()
            val circle = createCircle(Gen.int().generate(), x, y)
            circle.position shouldBe Point(x, y)
        }
        "contains point"{
            val circle = createCircle(5)
            circle should contains(Point(3, 4))
            circle should contains(Point(-1, -2))
            circle should contains(Point(0, 0))
            circle shouldNot contains(Point(6, 5))
            circle shouldNot contains(Point(4, -9))
            circle shouldNot contains(Point(-5, -5))
            circle shouldNot contains(Point(-7, -9))
        }
        "shift to other position"{
            val circle = createCircle(5)
            circle.shiftTo(Point(25, 30)) shouldBe createCircle(5, 25, 30)
            circle.shiftTo(Point(-57, 16)) shouldBe createCircle(5, -57, 16)
        }
        "circle equal"{
            for (i in 1..10) {
                val (x, y) = generateXY()
                val r = Gen.int().generate()

                val circle = createCircle(r, x, y)
                circle shouldBe Circle(r, Point(x, y))
                circle.hashCode() shouldBe createCircle(r, x, y).hashCode()
                circle.radius shouldBe r
            }
            val (x, y) = generateXY()
            val r = Gen.int().generate()
            createCircle(r, x, y) shouldNotBe Object()
            createCircle(r, x, y).equals(Object()) shouldBe false
        }
        "bounding rect"{
            createCircle(5).boundingRect() shouldBe createRect(10, 10)
            createCircle(13).boundingRect() shouldBe createRect(26, 26)
        }
    }

}