package moe.gogo.game.utils

import moe.gogo.game.shape.Rect
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

val EMPTY_IMAGE = BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR)

val DEFAULT_TEXT = "Text"

val DEFAULT_FONT = Font("Microsoft YaHei", Font.PLAIN, 16)

val DEFAULT_COLOR = parseColor("333333")

fun BufferedImage.draw(f: (Graphics2D) -> Unit) {
    val g = this.createGraphics()
    f(g)
    g.dispose()
}

/**
 * 抗锯齿
 */
fun Graphics2D.antialias() {
    this.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    this.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
}

/**
 * 构建指定大小的图片
 */
fun buildImage(rect: Rect): BufferedImage {
    with(rect) {
        return BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
    }
}

/**
 * 通过16进制的字符串创建Color
 */
fun parseColor(str: String): Color {
    val code = when (str.length) {
        3 -> {
            String(charArrayOf(str[0], str[0], str[1], str[1], str[2], str[2]))
        }
        6 -> str
        else -> throw IllegalArgumentException("$str is'nt color code")
    }
    return Color(code.toInt(16))
}