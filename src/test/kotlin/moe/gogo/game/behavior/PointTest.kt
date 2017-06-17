package moe.gogo.game.behavior

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec

class PointTest : StringSpec() {

    init {
        "point should equal"{
            (1..10).forEach {
                val (x1, y1) = generateXY()
                Point(x1, y1) shouldBe Point(x1, y1)
            }
        }
        "point should not equal"{
            (1..10).forEach {
                val (x1, y1) = generateXY()
                val (x2, y2) = generateXY()
                Point(x1, y1) shouldNotBe Point(x2, y2)
            }
        }
        "point should equal although have a little difference"{
            (1..10).forEach {
                val (x1, y1) = generateXY()
                Point(x1 - 0.02, y1 + 0.02) shouldBe Point(x1 + 0.02, y1 - 0.02)
            }
        }
        "plus point"{
            (1..10).forEach {
                val (x1, y1) = generateXY()
                val (x2, y2) = generateXY()
                Point(x1, y1) + Vector(x2, y2) shouldBe Point(x1 + x2, y1 + y2)
            }
        }
        "to vector"{
            (1..10).forEach {
                val (x1, y1) = generateXY()
                Point(x1, y1).toVector() shouldBe Vector(x1, y1)
            }
        }
    }

    fun generateXY() = Pair(Gen.float().generate(), Gen.float().generate())
}