package moe.gogo.game.utils

import java.lang.Math.abs

infix fun Float.equal(other: Float) = abs(this - other) <= 0.1