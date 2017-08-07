package moe.gogo.game.component

import moe.gogo.game.Observer
import moe.gogo.game.Subscriber
import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.LazyProperty
import moe.gogo.game.utils.MutativeValue
import moe.gogo.game.utils.Point

/**
 * 位置的组件
 *
 * PositionComponent通常表示的是容器的绝对位置，
 * 要获取容器的相对位置，请参考[RelativePositionComponent]
 *
 * 可以通过[onChange]方法或者[onChangeSubscriber]添加观察者，会在位置改变时触发
 *
 * @see RelativePositionComponent
 */
open class PositionComponent : Component() {

    /**
     * 该对象的位置
     */
    open var position: Point = EMPTY_POINT
        set(value) {
            if (value != position) {
                onChangeSubscriber.orNull()?.emit(MutativeValue(field, value))
                field = value
            }
        }

    /**
     * 位置改变时的订阅者
     */
    var onChangeSubscriber: LazyProperty<Subscriber<MutativeValue<Point>>> = LazyProperty({ Subscriber<MutativeValue<Point>>() })
        protected set

    /**
     * 观察位置变化信息
     * @param callback 位置改变时的回调函数
     * @return 注册的回调函数
     */
    fun onChange(callback: Observer<MutativeValue<Point>>): Observer<MutativeValue<Point>> {
        onChangeSubscriber.init()
        return onChangeSubscriber().subscribe(callback)
    }
}