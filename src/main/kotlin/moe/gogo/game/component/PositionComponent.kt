package moe.gogo.game.component

import moe.gogo.game.Observer
import moe.gogo.game.Subscriber
import moe.gogo.game.utils.LazyProperty
import moe.gogo.game.utils.Point

/**
 * 位置的组件
 *
 * 由于Entity可以被添加到另一个Entity上，PositionComponent通常表示的是父容器的相对位置
 *
 * 可以通过[onChange]方法或者[onChangeSubscriber]添加观察者，会在位置改变时触发
 */
open class PositionComponent : Component() {

    open var position: Point = Point(0, 0)
        set(value) {
            if (value != position) {
                onChangeSubscriber.orNull()?.emit(value)
                field = value
            }
        }

    var onChangeSubscriber: LazyProperty<Subscriber<Point>> = LazyProperty({ Subscriber<Point>() })
        protected set

    fun onChange(callback: (Point) -> Unit): Observer<Point> {
        onChangeSubscriber.init()
        return onChangeSubscriber().subscribe(callback)
    }
}