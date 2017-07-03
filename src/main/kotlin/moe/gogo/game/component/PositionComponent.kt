package moe.gogo.game.component

import moe.gogo.game.Observer
import moe.gogo.game.Subscriber
import moe.gogo.game.utils.Point


open class PositionComponent : Component() {

    open var position: Point = Point(0, 0)
        set(value) {
            if (value != position) {
                onChangeSubscriber?.emit(value)
                field = value
            }
        }

    private var onChangeSubscriber: Subscriber<Point>? = null

    fun onChange(callback: (Point) -> Unit): Observer<Point> {
        if (onChangeSubscriber == null) {
            onChangeSubscriber = Subscriber()
        }
        return onChangeSubscriber!!.subscribe(callback)
    }
}