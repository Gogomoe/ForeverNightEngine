package moe.gogo.game.input

import moe.gogo.game.input.MouseEventType.CLICK
import moe.gogo.game.input.MouseEventType.ENTER
import moe.gogo.game.input.MouseEventType.EXIT
import moe.gogo.game.input.MouseEventType.MOVE
import moe.gogo.game.input.MouseEventType.PRESS
import moe.gogo.game.input.MouseEventType.RELEASE
import moe.gogo.game.input.SimpleMouseEventType.ACTION

/**
 * 简化的鼠标事件类型，
 * 将[PRESS]、[RELEASE]、[CLICK]等归类于[动作][SimpleMouseEventType.ACTION]，
 * 将[ENTER]、[EXIT]、[MOVE]等归类于[移动][SimpleMouseEventType.MOVE]，
 */
enum class SimpleMouseEventType {
    ACTION, MOVE
}

private val action = setOf(CLICK, PRESS, RELEASE)
private val move = setOf(ENTER, EXIT, MOVE)

/**
 * 事件转换器，可以简化事件类型、转换事件类型，方便处理
 */
open class MouseEventConverter {

    /**
     * 获取鼠标事件的简化类型
     */
    open fun simplifyType(event: MouseEvent): SimpleMouseEventType =
            when (event.type) {
                in action -> ACTION
                in move -> SimpleMouseEventType.MOVE
                else -> throw IllegalArgumentException("${event.type} is illegal")
            }

    /**
     * 转化移动事件的类型，根据[inShape]、[inShapeBefore]判断具体是[ENTER]、[EXIT]、[MOVE]类型
     */
    open fun convert(event: MouseEvent, inShape: Boolean, inShapeBefore: Boolean): MouseEvent? {
        val enter = !inShapeBefore && inShape
        val exit = inShapeBefore && !inShape
        val move = inShapeBefore && inShape

        return when {
            enter -> buildWithType(event, ENTER)
            exit -> buildWithType(event, EXIT)
            move -> buildWithType(event, MOVE)
            else -> null
        }
    }

    open fun buildWithType(event: MouseEvent, type: MouseEventType)
            = object : MouseEvent(event.position, type) {

        override fun consume() {
            super.consume()
            // consume source event
            event.consume()
        }
    }

}