package moe.gogo.game.utils

import java.lang.Math.abs

infix fun Float.equal(other: Float) = abs(this - other) <= 0.1

infix fun Number.equal(other: Number) = this.toFloat() equal other.toFloat()