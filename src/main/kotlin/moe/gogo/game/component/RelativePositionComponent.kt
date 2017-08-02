package moe.gogo.game.component

import moe.gogo.game.utils.Point
import moe.gogo.game.utils.ReadOnlyDelegate
import moe.gogo.game.utils.ReadWriteDelegate
import moe.gogo.game.view.UIComponent

/**
 * 表示相对位置的组件
 *
 * 由于Entity可以被添加到另一个Entity上，[PositionComponent]获取的是相对于父容器的绝对位置，
 * 因此可以通过RelativePositionComponent操作Entity的相对位置
 *
 * 当Entity没有父容器时，RelativePosition等同于Position，
 * 修改Entity的相对位置，也会改变它的绝对位置
 *
 * @see PositionComponent
 */
abstract class RelativePositionComponent : Component() {

    abstract var position: Point

}

open class RelativePositionComponentImpl(
        val parentPosition: ReadOnlyDelegate<Point?>,
        val selfPosition: ReadWriteDelegate<Point>) : RelativePositionComponent() {

    override var position: Point
        get() {
            val p = parentPosition()
            return when (p) {
                null -> selfPosition()
                else -> selfPosition() - p.toVector()
            }
        }
        set(value) {
            val p = parentPosition()
            return when (p) {
                null -> selfPosition(value)
                else -> selfPosition(p + value.toVector())
            }
        }

}

fun RelativePositionComponent(component: UIComponent): RelativePositionComponent =
        RelativePositionComponentImpl(
                { component.parent?.position },
                ReadWriteDelegate(
                        { component.position },
                        { component.position = it }
                )
        )