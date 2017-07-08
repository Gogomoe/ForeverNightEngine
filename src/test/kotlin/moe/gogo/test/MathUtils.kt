package moe.gogo.test

import io.kotlintest.properties.Gen
import moe.gogo.game.utils.Point
import moe.gogo.game.utils.equal

fun notEqual(x1: Float, x2: Float, y1: Float, y2: Float): Boolean = !(x1 equal x2 && y1 equal y2)

fun generateXY() = Pair(Gen.float().generate(), Gen.float().generate())

fun generatePoint() = Point(Gen.float().generate(), Gen.float().generate())