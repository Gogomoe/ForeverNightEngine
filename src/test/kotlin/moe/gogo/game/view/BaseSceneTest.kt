package moe.gogo.game.view

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.input.MouseEvent
import moe.gogo.game.input.MouseEventHandler
import moe.gogo.game.input.MouseEventType.CLICK
import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point
import moe.gogo.test.TestStatus

class BaseSceneTest : StringSpec() {

    init {
        "create layer"{
            val scene: Scene = createScene()
            val layer1: Layer = scene.createLayer()

            scene.size shouldBe 1
            scene.layers[0] shouldBe layer1

            val layer2: Layer = scene.createLayer({ UILayer(scene) }, 0)

            scene.size shouldBe 2
            scene.layers[0] shouldBe layer2
            scene.layers[1] shouldBe layer1
        }
        "render and handle mouse event"{
            val scene: Scene = createScene()
            val status = TestStatus()
            scene.createLayer({ testLayer(status, scene) }, 0)
            scene.createLayer({ testLayer(status, scene) }, 0)
            // render 1          mouse event 5

            scene.render(createCamera())
            status shouldBe 2

            scene.mouseEventHandler(MouseEvent(EMPTY_POINT, CLICK))
            status shouldBe 7
        }
        "send consumed event"{
            val scene: Scene = createScene()
            val status = TestStatus()
            scene.createLayer({ testLayer(status, scene) }, 0)

            val e = MouseEvent(EMPTY_POINT, CLICK)
            e.consume()
            scene.mouseEventHandler(e)

            status shouldBe 0
        }
    }

    private fun testLayer(status: TestStatus, scene: Scene) = object : UILayer(scene) {
        override fun render(camera: Camera) {
            status.next(1)
        }

        override val mouseEventHandler: MouseEventHandler = object : MouseEventHandler {
            override fun handle(event: MouseEvent) {
                status.next(5)
                event.consume()
            }
        }
    }

    private fun createCamera(): Camera = object : Camera() {
        override fun shiftTo(point: Point) {}
    }

    private fun createScene() = BaseScene()

}