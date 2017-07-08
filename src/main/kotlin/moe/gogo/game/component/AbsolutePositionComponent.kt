package moe.gogo.game.component

import moe.gogo.game.utils.Point
import moe.gogo.game.utils.ReadOnlyDelegate
import moe.gogo.game.utils.ReadWriteDelegate
import moe.gogo.game.view.UIComponent

/**
 * 表示绝对位置的组件
 *
 * 由于Entity可以被添加到另一个Entity上，[PositionComponent]获取的是相对于父容器的相对位置，
 * 因此可以通过AbsolutePositionComponent操作Entity的绝对位置
 *
 * 当Entity没有父容器时，AbsolutePosition等同于Position
 *
 * 如果要改变某个Entity的父容器而试图保持绝对位置不改变，可以在sync中修改父容器
 */
abstract class AbsolutePositionComponent : Component() {

    abstract var position: Point

    open fun sync(callback: () -> Unit) {
        val pos = position
        callback()
        position = pos
    }

}

/**
 * AbsolutePositionComponent的默认实现，通过调用父容器的绝对位置于本容器的相对位置计算得到绝对位置
 */
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
                else -> selfPosition.set(value - p.toVector())
            }
        }

}

fun AbsolutePositionComponentImpl(container: UIComponent): AbsolutePositionComponent =
        AbsolutePositionComponentImpl(
                ReadOnlyDelegate { container.parent?.absolutePosition },
                ReadWriteDelegate(
                        { container.position },
                        { container.position = it }
                ))