package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.view.WindowFactoryImpl
import moe.gogo.test.couldRunSwing

class ScreenUtilsTest : StringSpec() {

    init {
        if (couldRunSwing()) {
            "screen size"{
                screenSize() shouldNotBe null
            }
            "center window"{
                val factory = WindowFactoryImpl()
                val window = factory.build()
                window.size(100, 100)
                window.center()

                window.x shouldBe (screenSize().width - 100) / 2
                window.y shouldBe (screenSize().height - 100) / 2
            }
        }

    }
}