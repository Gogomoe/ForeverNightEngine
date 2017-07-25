package moe.gogo.game.view

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNot
import io.kotlintest.specs.StringSpec
import moe.gogo.game.component.MouseEventComponent
import moe.gogo.game.component.RenderComponent
import moe.gogo.game.component.ShapeComponent
import moe.gogo.game.input.MouseEvent
import moe.gogo.game.input.MouseEventHandler
import moe.gogo.game.input.MouseEventType
import moe.gogo.game.input.MouseEventType.CLICK
import moe.gogo.game.input.MouseEventType.MOVE
import moe.gogo.game.input.MouseEventType.PRESS
import moe.gogo.game.input.MouseEventType.RELEASE
import moe.gogo.game.shape.Rect
import moe.gogo.game.shape.Shape
import moe.gogo.game.utils.EMPTY_IMAGE
import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point
import moe.gogo.test.TestStatus
import moe.gogo.test.`in`
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class UILayerTest : StringSpec() {

    init {
        "add and remove UIComponents"{
            val layer = createLayer()
            val component1 = createComponent()
            val component2 = createComponent()

            layer.add(component1)
            layer.add(component2)

            component1 should `in`(layer.components)
            component2 should `in`(layer.components)
            layer.components.size shouldBe 2

            layer.remove(component1)

            component1 shouldNot `in`(layer.components)
            layer.components.size shouldBe 1
        }

        "render components when call render"{
            val layer = createLayer()
            val state = TestStatus()
            val component1 = createRenderComponent(state)
            val camera = createCamera(100, 80)

            layer.add(component1)
            layer.render(camera)

            state shouldBe 1
            layer.image.width shouldBe 100
            layer.image.height shouldBe 80
        }

        "dispenser action mouse event"{
            val layer = createLayer()
            val shape = Rect(10, 10, EMPTY_POINT)
            val status = TestStatus()
            layer.add(createComponent())
            layer.add(createMouseEventComponent(shape, status))

            layer(createEvent(type = CLICK)) // 1
            layer(createEvent(type = PRESS)) // 2
            layer(createEvent(type = RELEASE)) // 3
            status shouldBe 6

            layer(createEvent(20, 20, type = CLICK))

            status shouldBe 6
        }
        "dispenser move mouse event"{
            val layer = createLayer()
            val shape = Rect(10, 10, EMPTY_POINT)
            val status = TestStatus()
            layer.add(createMouseEventComponent(shape, status))

            layer(createEvent(0, 0, type = MOVE))  // 6
            layer(createEvent(20, 20, type = MOVE)) // 5
            layer(createEvent(30, 30, type = MOVE))
            layer(createEvent(5, 5, type = MOVE))  // 4

            status shouldBe 15
        }
        "send consumed event"{
            val layer = createLayer()
            val shape = Rect(10, 10, EMPTY_POINT)
            val status = TestStatus()
            layer.add(createMouseEventComponent(shape, status))

            val e = createEvent()
            e.consume()

            layer.mouseEventHandler(e)
            status shouldBe 0
        }
    }


    private fun createComponent() = object : UIComponent() {}

    private fun createLayer() = UILayer(object : Scene() {
        override val layers: List<Layer>
            get() = nothing()

        override fun createLayer(builder: (Scene) -> Layer, index: Int): Layer = nothing()

        override fun createLayer(): Layer = nothing()

        override fun render(camera: Camera) = nothing()

        override val image: BufferedImage
            get() = nothing()
        override val mouseEventHandler: MouseEventHandler
            get() = nothing()

        fun nothing(): Nothing = throw NotImplementedError()
    })

    private fun createRenderComponent(state: TestStatus): UIComponent = object : UIComponent() {
        init {
            this[RenderComponent::class] = object : RenderComponent() {

                override fun render(graphics: Graphics2D) {
                    state.next()
                }

                override fun renderImage(): BufferedImage = EMPTY_IMAGE
            }
        }
    }

    private fun createCamera(width: Int = 100, height: Int = 80): Camera = object : Camera() {
        override val screenRange: Rect = Rect(width, height)
        override fun shiftTo(point: Point) {}
    }


    private fun createMouseEventComponent(shape: Rect, mouseEventComponent: MouseEventComponent = emptyMouseEventComponent()): UIComponent =
            object : UIComponent() {
                init {
                    this[ShapeComponent::class] = object : ShapeComponent() {
                        override val shape: Shape = shape
                    }
                    this[MouseEventComponent::class] = mouseEventComponent
                }
            }

    private fun createMouseEventComponent(shape: Rect, status: TestStatus): UIComponent = createMouseEventComponent(shape,
            object : MouseEventComponent() {
                override fun handleClick(event: MouseEvent) {
                    consume(event, 1)
                }

                override fun handlePress(event: MouseEvent) {
                    consume(event, 2)
                }

                override fun handleRelease(event: MouseEvent) {
                    consume(event, 3)
                }

                override fun handleEnter(event: MouseEvent) {
                    consume(event, 4)
                }

                override fun handleExit(event: MouseEvent) {
                    consume(event, 5)
                }

                override fun handleMove(event: MouseEvent) {
                    consume(event, 6)
                }

                fun consume(event: MouseEvent, value: Int) {
                    status.next(value)
                    event.consume()
                }
            }
    )


    private fun createEvent(x: Number = 0, y: Number = 0, type: MouseEventType = CLICK)
            = MouseEvent(Point(x, y), type)


    private fun emptyMouseEventComponent() = object : MouseEventComponent() {}

    private operator fun Layer.invoke(mouseEvent: MouseEvent) = this.mouseEventHandler(mouseEvent)
}