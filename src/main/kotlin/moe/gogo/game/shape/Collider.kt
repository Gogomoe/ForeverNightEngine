package moe.gogo.game.shape

import moe.gogo.game.utils.Vector
import java.lang.Math.abs

/**
 * 碰撞器，用于检验两个指定类型的形状之间有无接触部分
 *
 * 实现碰撞器后，需要注册到[ColliderRegistry]才能作为默认碰撞器使用
 */
abstract class Collider<in T : Shape, in S : Shape> {

    /**
     * 检验两个形状有无接触
     * @param t 第一个形状
     * @param s 第二个形状
     * @return 若两个形状接触，则返回true
     */
    abstract fun contact(t: T, s: S): Boolean

}

/**
 * 两个矩形之间的碰撞器
 */
class RectCollider : Collider<Rect, Rect>() {

    override fun contact(t: Rect, s: Rect): Boolean = RectCollider.contact(t, s)

    companion object {
        fun contact(t: Rect, s: Rect): Boolean {
            val (ox, oy) = t.position
            val (ow, oh) = t.halfFloat()
            val (x, y) = s.position
            val (w, h) = s.halfFloat()

            // | x1 - x2 | <= w1 + w2 && | y1 - y2 | <= y1 + y2
            return Math.abs(x - ox) <= w + ow && Math.abs(y - oy) <= h + oh
        }
    }

}

/**
 * 两个圆之间的碰撞器
 */
class CircleCollider : Collider<Circle, Circle>() {

    override fun contact(t: Circle, s: Circle): Boolean = CircleCollider.contact(t, s)

    companion object {
        fun contact(t: Circle, s: Circle): Boolean {
            val relative = (t.position.toVector() - s.position.toVector())
            return relative.length() <= (t.radius + s.radius)
        }
    }
}

/**
 * 矩形与圆的碰撞器
 */
class RectCircleCollider : Collider<Rect, Circle>() {

    override fun contact(t: Rect, s: Circle): Boolean = RectCircleCollider.contact(t, s)

    companion object {
        fun contact(rect: Rect, circle: Circle): Boolean {
            val relative = (circle.position.toVector() - rect.position.toVector())
            val positive = Vector(abs(relative.x), abs(relative.y))
            val diagonal = Vector(rect.width, rect.height)

            val difference = positive - diagonal
            // set negative value to 0
            val minLengthVector = Vector(maxOf(difference.x, 0f), maxOf(difference.y, 0f))

            return minLengthVector.length() <= circle.radius
        }
    }
}

/**
 * 圆与矩形的碰撞器
 */
class CircleRectCollider : Collider<Circle, Rect>() {

    override fun contact(t: Circle, s: Rect): Boolean = CircleRectCollider.contact(t, s)

    companion object {
        fun contact(circle: Circle, rect: Rect): Boolean = RectCircleCollider.contact(rect, circle)
    }
}