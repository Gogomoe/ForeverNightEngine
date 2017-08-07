package moe.gogo.game.component

import moe.gogo.game.input.MouseEvent
import moe.gogo.game.input.MouseEventType.CLICK
import moe.gogo.game.input.MouseEventType.ENTER
import moe.gogo.game.input.MouseEventType.EXIT
import moe.gogo.game.input.MouseEventType.MOVE
import moe.gogo.game.input.MouseEventType.PRESS
import moe.gogo.game.input.MouseEventType.RELEASE

/**
 * 监听鼠标事件的组件，实现本组件的容器可以监听到鼠标[点击][CLICK]、[按下][PRESS]、[移动][MOVE]等事件
 *
 * 能点击的组件必然有一定的形状、因此依赖于[形状组件][ShapeComponent]
 *
 * @see MouseEvent
 * @see ShapeComponent
 */
abstract class MouseEventComponent : Component() {

    override fun init(container: ComponentContainer) {
        super.init(container)
        dependOn<ShapeComponent>()
    }

    /**
     * 处理鼠标事件
     * @param event 发生的鼠标事件
     */
    open fun handle(event: MouseEvent) {
        when (event.type) {
            CLICK -> handleClick(event)
            PRESS -> handlePress(event)
            RELEASE -> handleRelease(event)
            ENTER -> handleEnter(event)
            EXIT -> handleExit(event)
            MOVE -> handleMove(event)
        }
    }

    open fun handleClick(event: MouseEvent) {}
    open fun handlePress(event: MouseEvent) {}
    open fun handleRelease(event: MouseEvent) {}
    open fun handleEnter(event: MouseEvent) {}
    open fun handleExit(event: MouseEvent) {}
    open fun handleMove(event: MouseEvent) {}

}