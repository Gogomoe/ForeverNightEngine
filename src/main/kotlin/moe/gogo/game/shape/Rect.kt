package moe.gogo.game.shape

import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point

/**
 * 矩形
 */
class Rect(val width: Int,
           val height: Int,
           override val position: Point = EMPTY_POINT) : Shape() {

    companion object {
        init {
            ColliderRegistry.register(RectCollider())
        }
    }

    override fun contains(point: Point): Boolean {
        val (hw, hh) = halfFloat()
        val (px, py) = point
        return px <= x + hw &&
                px >= x - hw &&
                py <= y + hh &&
                py >= y - hh
    }

    override fun contact(other: Shape): Boolean {
        return if (other is Rect) {
            RectCollider.contact(this, other)
        } else {
            super.contact(other)
        }
    }

    override fun boundingRect(): Rect = this

    override fun shiftTo(point: Point): Rect = Rect(width, height, point)

    fun half(): Pair<Int, Int> = Pair(width / 2, height / 2)
    fun halfFloat(): Pair<Float, Float> = Pair(width.toFloat() / 2, height.toFloat() / 2)

    operator fun component1(): Int = width
    operator fun component2(): Int = height

    override fun equals(other: Any?): Boolean = when (other) {
        is Rect -> {
            other.width == width && other.height == height && other.position == position
        }
        else -> false
    }

    override fun hashCode(): Int {
        return width * 27 + height * 31 + position.hashCode() * 43
    }

    override fun toString(): String {
        return "Rect($width, $height, $position)"
    }
}

