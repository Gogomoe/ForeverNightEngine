package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.test.equal

class MathTest : StringSpec() {

    init {
        "equal"{
            (1..10).forEach {
                val f = Gen.float().generate()
                f shouldNotBe equal(f + 17256)
                f shouldNotBe equal(f + .11f)
                f shouldBe equal(f + .09f)
                f shouldBe equal(f)
                f shouldBe equal(f - .05)
                f shouldBe equal(f - .099)
                f shouldNotBe equal(f - .13)
                f shouldNotBe equal(f - 2)
            }
        }
    }
}