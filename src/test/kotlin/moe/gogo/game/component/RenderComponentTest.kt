package moe.gogo.game.component

import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.view.UIComponent
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class RenderComponentTest : StringSpec() {

    init {
        "EmptyRenderComponent"{
            val empty = EmptyRenderComponent()
            val graphics = createGraphics()
            val container = createUIComponent()

            empty.render(graphics)
            empty.renderImage() shouldNotBe null

            container.render(graphics)
            container.renderComponent shouldNotBe null
        }
    }

    fun createUIComponent(): UIComponent = object : UIComponent() {}


    fun createGraphics(): Graphics2D = BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR).createGraphics()
}