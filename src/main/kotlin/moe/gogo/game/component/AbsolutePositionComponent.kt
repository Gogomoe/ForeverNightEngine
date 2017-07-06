package moe.gogo.game.component

import moe.gogo.game.utils.Point
import moe.gogo.game.utils.ReadOnlyDelegate
import moe.gogo.game.utils.ReadWriteDelegate
import moe.gogo.game.view.UIComponent

abstract class AbsolutePositionComponent : Component() {

    abstract var position: Point

}

class AbsolutePositionComponentImpl(val parentPosition: ReadOnlyDelegate<Point?>, val selfPosition: ReadWriteDelegate<Point>) : AbsolutePositionComponent() {

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
                AbsolutePositionComponentImpl(
                        ReadOnlyDelegate { container.parent?.absolutePosition },
                        ReadWriteDelegate(
                                { container.position },
                                { container.position = it }
                        ))

    }

}
