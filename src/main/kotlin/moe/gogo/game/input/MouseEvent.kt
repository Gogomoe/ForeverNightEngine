package moe.gogo.game.input

import moe.gogo.game.event.Event
import moe.gogo.game.utils.Point

/**
 * 鼠标事件拥有发生的[位置][position]和一个[类型][MouseEventType]
 */
open class MouseEvent(val position: Point, val type: MouseEventType) : Event()

/**
 * 描述鼠标事件的类型，可以是
 * [点击][MouseEventType.CLICK]、[按下][MouseEventType.PRESS]、[释放][MouseEventType.RELEASE]、
 * [进入][MouseEventType.ENTER]、[离开][MouseEventType.EXIT]、[移动][MouseEventType.MOVE]
 */
enum class MouseEventType {
    CLICK, PRESS, RELEASE, ENTER, EXIT, MOVE
}