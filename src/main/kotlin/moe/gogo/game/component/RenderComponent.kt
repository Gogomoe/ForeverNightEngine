package moe.gogo.game.component

import moe.gogo.game.utils.EMPTY_IMAGE
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * 用于渲染的组件
 */
abstract class RenderComponent : Component() {

    /**
     * 将组件渲染并绘制到[graphics]上
     */
    abstract fun render(graphics: Graphics2D)

    /**
     * 获取当前渲染的图片
     */
    abstract fun renderImage(): BufferedImage

}

/**
 * 渲染的空实现
 */
fun EmptyRenderComponent(): RenderComponent = object : RenderComponent() {

    override fun render(graphics: Graphics2D) {
    }

    override fun renderImage(): BufferedImage = EMPTY_IMAGE

}