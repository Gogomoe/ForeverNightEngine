package moe.gogo.game.view

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.shape.Rect
import moe.gogo.game.utils.EMPTY_IMAGE
import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.test.TestStatus
import moe.gogo.test.generatePoint
import java.awt.Graphics
import java.awt.image.BufferedImage

class PanelImplTest : StringSpec() {

    init {
        "load and unload scene"{
            val panel = createPanel()
            val scene = createScene()

            panel.load(scene)
            panel.scene shouldBe scene

            panel.unload()
            panel.scene shouldBe null
        }
        "set size and get camera"{
            val panel = createPanel()
            val width = 960
            val height = 640
            panel.setSize(width, height)
            panel.camera.screenRange.shiftTo(EMPTY_POINT) shouldBe Rect(width, height)
            panel.camera.gameRange.shiftTo(EMPTY_POINT) shouldBe Rect(width, height)
        }
        "shift to other position"{
            val panel = createPanel()
            val point = generatePoint()
            panel.camera.shiftTo(point)
            panel.camera.position shouldBe point
        }
        "paint component"{
            val panel = createPanel()
            val state = TestStatus()

            panel.setSize(10, 10)
            val func = panel::class.java.getDeclaredMethod("paintComponent", Graphics::class.java)
            func.invoke(panel, EMPTY_IMAGE.createGraphics())

            panel.load(listeningImageScene(state))
            func.invoke(panel, EMPTY_IMAGE.createGraphics())

            state shouldBe 1
        }
    }


    private fun createPanel(): Panel = PanelImpl()

    private fun createScene(): Scene = BaseScene()

    private fun listeningImageScene(state: TestStatus): Scene = object : BaseScene() {
        override var image: BufferedImage = super.image
            get() {
                state.next()
                return super.image
            }
    }


}