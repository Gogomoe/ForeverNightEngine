package moe.gogo.game.shape

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNot
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.game.utils.Point
import moe.gogo.test.equal
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

        "shift to other position"{
            val r1 = createRect(10, 20, 0, 0)
            r1.shiftTo(Point(25, 30)) shouldBe createRect(10, 20, 25, 30)
            r1.shiftTo(Point(-57, 16)) shouldBe createRect(10, 20, -57, 16)
        }
        "half and half float"{
            val rect = createRect(Gen.int().generate(), Gen.int().generate())
            val (w, h) = rect
            rect.half().first shouldBe w / 2
            rect.half().second shouldBe h / 2
            rect.halfFloat().first should equal(w.toFloat() / 2)
            rect.halfFloat().second should equal(h.toFloat() / 2)
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
        "bounding rect"{
            val rect = Rect(7, 8)
            rect.boundingRect() shouldBe rect
        }
        "shape contact by rect"{
            val a = ShapeA(Rect(10, 10))
            val b = ShapeB(Rect(10, 10))
            a should contact(b)
            ColliderRegistry.register(BACollider())
            a should contact(b)
            ColliderRegistry.register(ABCollider())
            a should contact(b)
        }

    }

}