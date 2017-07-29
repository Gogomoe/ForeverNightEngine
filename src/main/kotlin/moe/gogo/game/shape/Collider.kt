package moe.gogo.game.shape

import moe.gogo.game.utils.Vector
import java.lang.Math.abs

abstract class Collider<in T : Shape, in S : Shape> {

    abstract fun contact(t: T, s: S): Boolean

}

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

class CircleCollider : Collider<Circle, Circle>() {

    override fun contact(t: Circle, s: Circle): Boolean = CircleCollider.contact(t, s)

    companion object {
        fun contact(t: Circle, s: Circle): Boolean {
            val relative = (t.position.toVector() - s.position.toVector())
            return relative.length() <= (t.radius + s.radius)
        }
    }
}

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


class CircleRectCollider : Collider<Circle, Rect>() {

    override fun contact(t: Circle, s: Rect): Boolean = CircleRectCollider.contact(t, s)

    companion object {
        fun contact(circle: Circle, rect: Rect): Boolean = RectCircleCollider.contact(rect, circle)
    }
}