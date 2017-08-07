package moe.gogo.game.view

import moe.gogo.game.component.MouseEventComponent
import moe.gogo.game.component.RenderComponent
import moe.gogo.game.component.ShapeComponent
import moe.gogo.game.shape.Rect
import moe.gogo.game.utils.DEFAULT_COLOR
import moe.gogo.game.utils.DEFAULT_FONT
import moe.gogo.game.utils.DEFAULT_TEXT
import moe.gogo.game.utils.EMPTY_IMAGE
import moe.gogo.game.utils.EMPTY_RECT
import moe.gogo.game.utils.antialias
import moe.gogo.game.utils.draw
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * 文本UI组件，可以绘制出单行文本，文本以[position]为中心
 *
 * 文本绘制有缓存，只有当[text]、[font]、[color]等外观改变时，文本才会重新绘制
 *
 * 实现了[ShapeComponent]，可以获取文本矩形的大小，
 * 添加[MouseEventComponent]后可以被处理鼠标事件
 *
 * @param text 文本内容
 * @param font 文本的字体
 * @param color 文本颜色
 */
open class Text constructor(var text: String = DEFAULT_TEXT,
                            var font: Font = DEFAULT_FONT,
                            var color: Color = DEFAULT_COLOR) : UIComponent() {

    open val render: TextRender = TextRender(this)
    open val shape: Rect
        get() = get<ShapeComponent>().shape as Rect

    init {
        set<RenderComponent>(render)
        set<ShapeComponent>(TextShapeComponent(this))
    }

    /**
     * 文本渲染器，具有缓存的能力，仅当文本的[text]、[font]、[color]等外观发生改变时重新渲染，
     * 在渲染同时会计算文本大小
     *
     * @param text 渲染的文本对象
     */
    open class TextRender(val text: Text) : RenderComponent() {
        private var strCache: String? = null
        private var fontCache: Font? = null
        private var colorCache: Color? = null

        var fontSize: Rect = EMPTY_RECT
        private var ascent: Int = 0
        private var imageCache: BufferedImage = EMPTY_IMAGE


        override fun render(graphics: Graphics2D) {
            val image = renderImage()
            val (x, y) = text.position.int()
            val (w, h) = text.shape
            // 绘制时以 Position 为中心
            graphics.drawImage(image, x - w / 2, y - h / 2, null)
        }

        override fun renderImage(): BufferedImage {
            return if (textHasChanged()) {
                computeTextSize()
                // 重绘被更新缓存
                repaint().also { updateCache(it) }
            } else {
                imageCache
            }
        }

        private fun computeTextSize() {
            EMPTY_IMAGE.draw { g ->
                val metrics = g.getFontMetrics(text.font)
                fontSize = Rect(metrics.stringWidth(text.text), metrics.height)
                ascent = metrics.ascent
            }
        }

        private fun updateCache(image: BufferedImage) = with(text) {
            strCache = text
            fontCache = font
            colorCache = color
            imageCache = image
        }

        private fun repaint(): BufferedImage {
            val (w, h) = fontSize
            val image = BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR)
            image.draw { g ->
                g.font = text.font
                g.color = text.color
                g.antialias()
                g.drawString(text.text, 0, ascent)
            }
            return image
        }

        private fun textHasChanged(): Boolean = with(text) {
            (strCache != text) || (fontCache != font) || (colorCache != color)
        }

    }

    private class TextShapeComponent(val text: Text) : ShapeComponent() {
        override val shape: Rect
            get() {
                if (text.render.fontSize == EMPTY_RECT) {
                    text.renderComponent.renderImage()
                }
                return text.render.fontSize.shiftTo(text.position)
            }
    }

}