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
 * @param rect 图片的大小
 * @return 指定大小的图片
 */
fun buildImage(rect: Rect): BufferedImage {
    with(rect) {
        return BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
    }
}

/**
 * 通过16进制的字符串创建Color
 *
 * 若颜色前有个"#"号，则会忽略
 *
 * 只支持3位或6位的颜色代码，例如
 * parseColor("a37") == parseColor("aa3377")
 * parseColor("#ffffff") == Color.white
 *
 * @param 16进制的颜色代码
 * @return 颜色
 * @throws IllegalArgumentException 颜色位数错误
 */
fun parseColor(str: String): Color {
    var code = str
    if (code.startsWith("#")) {
        code = code.substring(1)
    }
    code = when (code.length) {
        3 -> {
            String(charArrayOf(code[0], code[0], code[1], code[1], code[2], code[2]))
        }
        6 -> code
        else -> throw IllegalArgumentException("$str is'nt color code")
    }
    return Color(code.toInt(16))
}