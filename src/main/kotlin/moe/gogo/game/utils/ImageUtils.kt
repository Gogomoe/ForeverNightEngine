package moe.gogo.game.utils

import moe.gogo.game.shape.Rect
import java.awt.image.BufferedImage

val EMPTY_IMAGE = BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR)

/**
 * 构建指定大小的图片
 */
fun buildImage(rect: Rect): BufferedImage {
    with(rect) {
        return BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
    }
}