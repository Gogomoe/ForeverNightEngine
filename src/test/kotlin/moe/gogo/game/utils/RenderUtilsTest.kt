package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.game.shape.Rect
import java.awt.Color

class RenderUtilsTest : StringSpec() {

    init {
        "build image"{
            val rect: Rect = Rect(Gen.choose(0, 1000).generate(), Gen.choose(0, 1000).generate(), EMPTY_POINT)
            val image = buildImage(rect)
            image.width shouldBe rect.width
            image.height shouldBe rect.height
        }
        "build color"{
            parseColor("000000") shouldBe Color.black
            parseColor("fff") shouldBe Color.white
            shouldThrow<IllegalArgumentException> {
                parseColor("abcd")
            }
        }
        "default values"{
            DEFAULT_TEXT shouldNotBe null
            DEFAULT_COLOR shouldNotBe null
            DEFAULT_FONT shouldNotBe null
            EMPTY_IMAGE shouldNotBe null
        }
        "draw and antialiaing"{
            EMPTY_IMAGE.draw { g ->
                g.antialias()
            }
        }
    }

}