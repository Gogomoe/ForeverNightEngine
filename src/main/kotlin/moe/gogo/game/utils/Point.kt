package moe.gogo.game.utils

class Point(val x: Float, val y: Float) {

    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())

    fun setX(x: Number): Point = Point(x, y)
    fun setY(y: Number): Point = Point(x, y)

    operator fun plus(v: Vector): Point = Point(x + v.x, y + v.y)

    operator fun minus(v: Vector): Point = Point(x - v.x, y - v.y)

    fun toVector(): Vector = Vector(x, y)

    override fun equals(other: Any?): Boolean = when (other) {
        is Point -> {
            x equal other.x && y equal other.y && hashCode() == other.hashCode()
        }
        else -> false
    }

    operator fun component1(): Float = x
    operator fun component2(): Float = y

    fun int() = Pair(x.toInt(), y.toInt())

    override fun hashCode(): Int = (x * 0.1).toInt() * 47 + (y * 0.1).toInt() * 29

    override fun toString(): String = "(%.1f, %.1f)".format(x, y)

}