package moe.gogo.game.view

import moe.gogo.game.input.MouseEventType
import moe.gogo.game.input.MouseEventType.CLICK
import moe.gogo.game.input.MouseEventType.MOVE
import moe.gogo.game.input.MouseEventType.PRESS
import moe.gogo.game.input.MouseEventType.RELEASE
import moe.gogo.game.utils.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

/**
 * 创建[Panel]的工厂，[Panel]初始化时通常需要添加一堆例如 MouseListener、KeyboardListener 之类的东西
 */
abstract class PanelFactory {

    abstract fun build(): Panel

}

/**
 * 默认的[PanelFactory]实现
 *
 * 添加了MouseListener，会将MouseEvent转发给Scene
 */
class PanelFactoryImpl : PanelFactory() {

    override fun build(): Panel {
        val panel: Panel =  PanelImpl()

        addMouseListener(panel)
        // TODO Keyboard event listener

        return panel
    }

    private fun addMouseListener(panel: Panel) {
        panel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                emit(CLICK, e.toPoint())
            }

            override fun mousePressed(e: MouseEvent?) {
                emit(PRESS, e.toPoint())
            }

            override fun mouseReleased(e: MouseEvent?) {
                emit(RELEASE, e.toPoint())
            }

            override fun mouseMoved(e: MouseEvent?) {
                emit(MOVE, e.toPoint())
            }

            override fun mouseEntered(e: MouseEvent?) {
                emit(MOVE, e.toPoint())
            }

            override fun mouseDragged(e: MouseEvent?) {
                emit(MOVE, e.toPoint())
            }

            override fun mouseExited(e: MouseEvent?) {
                emit(MOVE, e.toPoint())
            }

            fun MouseEvent?.toPoint() = Point(this!!.point!!.x, this.point!!.y)

            private fun emit(type: MouseEventType, point: Point) {
                val event = moe.gogo.game.input.MouseEvent(point, type)
                panel.scene?.mouseEventHandler?.handle(event)
            }
        })
    }

}