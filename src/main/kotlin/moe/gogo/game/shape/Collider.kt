package moe.gogo.game.shape

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