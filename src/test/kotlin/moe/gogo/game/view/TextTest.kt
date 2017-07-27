package moe.gogo.game.view

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.utils.EMPTY_IMAGE
import moe.gogo.game.utils.EMPTY_RECT
import moe.gogo.game.utils.draw
import moe.gogo.test.bigger
import java.awt.Color
import java.awt.Font

class TextTest : StringSpec() {

    init {
        "render text"{
            val text: Text = createText()
            val image = text.renderComponent.renderImage()
            image.width should bigger(0)
            image.height should bigger(0)

            EMPTY_IMAGE.draw {
                text.render(it)
                text.render(it) // render repeatedly
            }
        }
        "text size"{
            val text: Text = createText()
            text.shape shouldNotBe EMPTY_RECT
            text.shape shouldNotBe EMPTY_RECT // read shape cache
        }
        "render when change"{
            val text: Text = createText()
            val image = text.renderComponent.renderImage()
            text.text = "hello world"
            text.font = Font("Microsoft YaHei", Font.PLAIN, 20)
            text.color = Color.red
            text.renderComponent.renderImage() shouldNotBe image
        }
    }

    private fun createText(): Text = Text("testing string")

}
