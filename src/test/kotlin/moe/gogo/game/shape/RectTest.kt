package moe.gogo.game.shape

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNot
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.game.utils.Point
import moe.gogo.test.generateWH
import moe.gogo.test.generateXY

class RectTest : StringSpec() {

    init {
        "position"{
            val (x, y) = generateXY()
            val rect = createRect(Gen.int().generate(), Gen.int().generate(), x, y)
            rect.position shouldBe Point(x, y)
        }
        "contains point"{
            val rect = createRect(10, 10, 0, 0)
            rect should contains(Point(3, 4))
            rect should contains(Point(-5, -5))
            rect should contains(Point(0, 0))
            rect shouldNot contains(Point(6, 5))
            rect shouldNot contains(Point(4, -9))
            rect shouldNot contains(Point(-3, 13))
            rect shouldNot contains(Point(-7, -9))
        }
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
        "shift to other position"{
            val r1 = createRect(10, 20, 0, 0)
            r1.shiftTo(Point(25, 30)) shouldBe createRect(10, 20, 25, 30)
            r1.shiftTo(Point(-57, 16)) shouldBe createRect(10, 20, -57, 16)
        }
        "rect equal"{
            for (i in 1..10) {
                val (x, y) = generateXY()
                val (w, h) = generateWH()

                val rect = createRect(w, h, x, y)
                rect shouldBe createRect(w, h, x, y)
                rect.hashCode() shouldBe createRect(w, h, x, y).hashCode()
                rect.width shouldBe w
                rect.height shouldBe h
            }
            val (x, y) = generateXY()
            val (w, h) = generateWH()
            createRect(w, h, x, y) shouldNotBe Object()
            createRect(w, h, x, y).equals(Object()) shouldBe false

        }

    }

    fun createRect(w: Int, h: Int, x: Number, y: Number) = Rect(w, h, Point(x, y))
}