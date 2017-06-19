package moe.gogo.game.behavior

import moe.gogo.game.FObject

open class PositionBehavior {

    open var point: Point = Point(0f, 0f)

    var x: Float
        get() = point.x
        set(value) {
            point = Point(value, y)
        }

    var y: Float
        get() = point.y
        set(value) {
            point = Point(x, value)
        }

    operator fun plus(vector: Vector) {
        point += vector
    }

    operator fun minus(vector: Vector) {
        point -= vector
    }

}

class AbsolutePositionBehavior(val self: FObject) : PositionBehavior() {

    override var point: Point
        get() {
            val parent: FObject? = self.parent
            if (parent != null) {
                return calcAbsolutePosition(parent)
            } else {
                return relativePosition()
            }
        }
        set(value) {
            val parent: FObject? = self.parent
            if (parent != null) {
                self.position.point = calcRelativePosition(parent, value)
            } else {
                self.position.point = value
            }
        }

    private fun relativePosition(): Point = self.position.point

    private fun calcRelativePosition(parent: FObject, value: Point): Point
            = value - parent.absolutePosition.point.toVector()

    private fun calcAbsolutePosition(parent: FObject): Point
            = parent.absolutePosition.point + self.position.point.toVector()


}