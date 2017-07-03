package moe.gogo.game.component

import java.awt.Graphics2D
import java.awt.image.BufferedImage


abstract class RenderComponent : Component() {

    abstract fun render(graphics2D: Graphics2D)

    abstract fun renderImage(): BufferedImage

}

val EMPTY_IMAGE = BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR)

fun EmptyRenderComponent(): RenderComponent = object : RenderComponent() {

    override fun render(graphics2D: Graphics2D) {
    }

    override fun renderImage(): BufferedImage = EMPTY_IMAGE

}