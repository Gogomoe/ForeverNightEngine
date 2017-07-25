package moe.gogo.game.view

import moe.gogo.game.component.MouseEventComponent
import moe.gogo.game.component.ShapeComponent
import moe.gogo.game.input.MouseEvent
import moe.gogo.game.input.MouseEventConverter
import moe.gogo.game.input.MouseEventHandler
import moe.gogo.game.input.SimpleMouseEventType.ACTION
import moe.gogo.game.input.SimpleMouseEventType.MOVE
import moe.gogo.game.utils.EMPTY_IMAGE
import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point
import moe.gogo.game.utils.buildImage
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * 用于添加[UI组件][UIComponent]的Layer
 *
 * 默认的[mouseEventHandler]实现方式是分发给UIComponent处理
 */
open class UILayer constructor(scene: Scene) : Layer(scene) {

    val components: MutableSet<UIComponent> = mutableSetOf()

    open fun add(component: UIComponent) = components.add(component)
    open fun remove(component: UIComponent) = components.remove(component)

    override fun render(camera: Camera) {
        val image: BufferedImage = buildImage(camera.screenRange)
        val graphics: Graphics2D = image.createGraphics()
        components.forEach { it.render(graphics) }
        this.image = image
    }

    override var image: BufferedImage = EMPTY_IMAGE

    override val mouseEventHandler: MouseEventHandler
            = UIComponentMouseEventHandler({ this.components })

    /**
     * 实现鼠标事件处理的内部类
     */
    private class UIComponentMouseEventHandler(private val components: () -> Collection<UIComponent>) : MouseEventHandler {

        private var lastPosition: Point = EMPTY_POINT

        private val converter: MouseEventConverter = MouseEventConverter()

        override fun handle(event: MouseEvent) {
            if (event.consumed) {
                return
            }
            components()
                    .filter(this::isObserveMouseEvent)
                    .forEach {
                        handleThoughComponent(event, it)
                        if (event.consumed) {
                            lastPosition = event.position
                            // break the [handle] function
                            return
                        }
                    }
            lastPosition = event.position
        }

        private fun isObserveMouseEvent(component: UIComponent) = component.has<MouseEventComponent>()

        private fun handleThoughComponent(event: MouseEvent, component: UIComponent)
                = when (converter.simplifyType(event)) {
            ACTION -> handleActionEvent(event, component)
            MOVE -> handleMoveEvent(event, component)
        }

        private fun handleActionEvent(event: MouseEvent, component: UIComponent) {
            val inShape = event.position in component.get<ShapeComponent>()
            if (inShape) {
                component.get<MouseEventComponent>().handle(event)
            }
        }

        private fun handleMoveEvent(event: MouseEvent, component: UIComponent) {
            val shape = component.get<ShapeComponent>()
            val inShape = event.position in shape
            val inShapeBefore = lastPosition in shape

            val newEvent = converter.convert(event, inShape, inShapeBefore)
            newEvent?.let { component.get<MouseEventComponent>().handle(it) }
        }

    }


}