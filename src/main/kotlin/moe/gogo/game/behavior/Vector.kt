package moe.gogo.game.behavior

import moe.gogo.game.utils.equal
import java.lang.Math.pow
import java.lang.Math.sqrt

class Vector(val x: Float, val y: Float) {

    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())

    operator fun plus(v: Vector): Vector = Vector(x + v.x, y + v.y)

    operator fun minus(v: Vector): Vector = Vector(x - v.x, y - v.y)

    fun length(): Float = sqrt(pow(x.toDouble(), 2.0) + pow(y.toDouble(), 2.0)).toFloat()

    operator fun times(num: Number): Vector = Vector(x * num.toFloat(), y * num.toFloat())

    fun toPoint(): Point = Point(x, y)

    override fun equals(other: Any?): Boolean = when (other) {
        is Vector -> {
            x equal other.x && y equal other.y
        }
        else -> false
    }

    override fun hashCode(): Int = (x * 47 + y * 29).toInt()

    override fun toString(): String = "(%.1f, %.1f)".format(x, y)
}