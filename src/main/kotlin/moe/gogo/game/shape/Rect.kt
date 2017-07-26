package moe.gogo.game.shape

import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point
import java.lang.Math.abs

/**
 * 矩形
 */
class Rect(val width: Int,
           val height: Int,
           override val position: Point = EMPTY_POINT) : Shape() {

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

        // | x1 - x2 | <= w1 + w2 && | y1 - y2 | <= y1 + y2
        return abs(x - ox) <= w + ow && abs(y - oy) <= h + oh
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

    operator fun component1(): Int = width
    operator fun component2(): Int = height

    override fun toString(): String {
        return "Rect($width, $height, $position)"
    }
}

