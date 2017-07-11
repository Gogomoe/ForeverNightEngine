package moe.gogo.game.utils

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
            x equal other.x && y equal other.y && hashCode() == other.hashCode()
        }
        else -> false
    }

    override fun hashCode(): Int = (x * 0.1).toInt() * 47 + (y * 0.1).toInt() * 29

    override fun toString(): String = "(%.1f, %.1f)".format(x, y)
}