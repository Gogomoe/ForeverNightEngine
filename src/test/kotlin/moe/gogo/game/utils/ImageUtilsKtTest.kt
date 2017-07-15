package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.game.shape.Rect

class ImageUtilsKtTest : StringSpec() {

    init {
        "build image"{
            val rect: Rect = Rect(Gen.choose(0, 1000).generate(), Gen.choose(0, 1000).generate(), EMPTY_POINT)
            val image = buildImage(rect)
            image.width shouldBe rect.width
            image.height shouldBe rect.height
        }
    }

}