package moe.gogo.game.shape

import moe.gogo.game.utils.Point

/**
 * 矩形
 */
class Rect(val width: Int,
           val height: Int,
           override val position: Point) : Shape {

    override fun contains(point: Point): Boolean {
        val hw = width.toFloat() / 2
        val hh = height.toFloat() / 2
        val px = point.x
        val py = point.y
        return px <= x + hw &&
                px >= x - hw &&
                py <= y + hh &&
                py >= y - hh
    }

    override fun contact(other: Shape): Boolean {
        val otherRect = other.boundingRect()
        val ox = otherRect.x
        val oy = otherRect.y
        val ow = otherRect.width.toFloat() / 2
        val oh = otherRect.height.toFloat() / 2
        val w = width.toFloat() / 2
        val h = height.toFloat() / 2

        return x + w >= ox - ow && ox + ow >= x - w &&
                y + h >= oy - oh && oy + oh >= y - h
    }

    override fun boundingRect(): Rect = this

    override fun shiftTo(point: Point): Rect = Rect(width, height, point)

    override fun equals(other: Any?): Boolean = when (other) {
        is Rect -> {
            other.width == width && other.height == height && other.position == position
        }
        else -> false
    }

    override fun hashCode(): Int {
        return width * 27 + height * 31 + position.hashCode() * 43
    }


}
