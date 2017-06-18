package moe.gogo.game.behavior

import moe.gogo.game.FObject
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * Renderer接口用于渲染
 *
 * 通常使用 renderImage 方法产生渲染的图片
 * 然后在 render 中绘制该图片
 */
interface Renderer {

    fun renderImage(): BufferedImage

    fun render(graphics: Graphics2D): Unit

}

abstract class RenderdBehavior(val self: FObject) : Renderer