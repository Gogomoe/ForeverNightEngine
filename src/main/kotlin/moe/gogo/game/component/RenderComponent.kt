package moe.gogo.game.component

import moe.gogo.game.utils.EMPTY_IMAGE
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * 用于渲染的组件
 *
 * 通常是在[render]方法中调用[renderImage]获取渲染的图片，
 * 然后决定绘制的大小、位置
 */
abstract class RenderComponent : Component() {

    /**
     * 将组件渲染并绘制到[graphics]上
     *
     * 通常这个方法是决定绘制的大小、位置，调用[renderImage]获取图片后，再绘制到画布上
     *
     * @param graphics 需要被绘制的画布
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