package moe.gogo.game.shape

import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point

/**
 * 圆
 * @param radius 圆的半径
 * @param position 圆心的位置
 */
class Circle(val radius: Int,
             override val position: Point = EMPTY_POINT) : Shape() {

    companion object {
        init {
            ColliderRegistry.register(CircleCollider())
            ColliderRegistry.register(RectCircleCollider())
            ColliderRegistry.register(CircleRectCollider())
        }
    }

    override fun contains(point: Point): Boolean
            = (point.toVector() - position.toVector()).length() <= radius

    override fun boundingRect(): Rect = Rect(radius * 2, radius * 2, position)

    override fun shiftTo(point: Point): Circle = Circle(radius, point)


    override fun equals(other: Any?): Boolean = when (other) {
        is Circle -> {
            other.radius == radius && other.position == position
        }
        else -> false
    }

    override fun hashCode(): Int {
        return radius * 67 + position.hashCode() * 17
    }

    override fun toString(): String {
        return "Circle($radius, $position)"
    }
}