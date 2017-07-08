package moe.gogo.game.component

import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * 用于渲染的组件
 */
abstract class RenderComponent : Component() {

    abstract fun render(graphics2D: Graphics2D)

    /**
     * 获取当前渲染的图片
     */
    abstract fun renderImage(): BufferedImage

}

val EMPTY_IMAGE = BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR)
/**
 * 渲染的空实现
 */
fun EmptyRenderComponent(): RenderComponent = object : RenderComponent() {

    override fun render(graphics2D: Graphics2D) {
    }

    override fun renderImage(): BufferedImage = EMPTY_IMAGE

}