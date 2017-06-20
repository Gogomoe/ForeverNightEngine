package moe.gogo.game.behavior

import moe.gogo.game.FObject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PositionProperty : ReadWriteProperty<FObject, Point>

class PositionBehavior : PositionProperty {

    var point: Point = Point(0f, 0f)

    override fun getValue(thisRef: FObject, property: KProperty<*>) = point

    override fun setValue(thisRef: FObject, property: KProperty<*>, value: Point) {
        point = value
    }

}

class AbsolutePositionBehavior : PositionProperty {
    override fun getValue(thisRef: FObject, property: KProperty<*>): Point {
        val parent: FObject? = thisRef.parent
        if (parent != null) {
            return thisRef.calcAbsolutePosition(parent)
        } else {
            return thisRef.relativePosition()
        }
    }

    override fun setValue(thisRef: FObject, property: KProperty<*>, value: Point) {
        val parent: FObject? = thisRef.parent
        if (parent != null) {
            thisRef.position = calcRelativePosition(parent, value)
        } else {
            thisRef.position = value
        }
    }

    private fun FObject.relativePosition(): Point = this.position

    private fun calcRelativePosition(parent: FObject, value: Point): Point
            = value - parent.absolutePosition.toVector()

    private fun FObject.calcAbsolutePosition(parent: FObject): Point
            = parent.absolutePosition + this.position.toVector()


}