package moe.gogo.game.component

import moe.gogo.game.utils.Point
import moe.gogo.game.view.UIComponent
import kotlin.reflect.KMutableProperty0

abstract class AbsolutePositionComponent : Component() {

    abstract var position: Point

}

class AbsolutePositionComponentImpl(val parentPosition: () -> Point?, val selfPosition: KMutableProperty0<Point>) : AbsolutePositionComponent() {

    override var position: Point
        get() {
            val p = parentPosition()
            return when (p) {
                null -> selfPosition.get()
                else -> p + selfPosition.get().toVector()
            }
        }
        set(value) {
            val p = parentPosition()
            when (p) {
                null -> selfPosition.set(value)
                else -> selfPosition.set(p - value.toVector())
            }
        }

    companion object {
        operator fun invoke(container: UIComponent): AbsolutePositionComponent =
                AbsolutePositionComponentImpl({ container.parent?.absolutePosition }, container::position)

    }

}
