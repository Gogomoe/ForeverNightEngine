package moe.gogo.game.behavior

import moe.gogo.game.utils.equal

class Point(val x: Float, val y: Float) {

    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())

    operator fun plus(v: Vector): Point = Point(x + v.x, y + v.y)

    fun toVector(): Vector = Vector(x, y)

    override fun equals(other: Any?): Boolean = when (other) {
        is Point -> {
            x equal other.x && y equal other.y
        }
        else -> false
    }

    override fun hashCode(): Int = (x * 47 + y * 29).toInt()

    override fun toString(): String = "(%.1f, %.1f)".format(x, y)
}