package moe.gogo.game.view

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.test.couldRunSwing
import javax.swing.JFrame

class WindowFactoryImplTest : StringSpec() {

    init {
        if (couldRunSwing()) {
            "build window"{
                val factory = WindowFactoryImpl()
                val window = factory.build()

                window {
                    title = "ForeverNightEngine"
                    size(800, 600)
                }

                window.width shouldBe 800
                window.height shouldBe 600
                window.defaultCloseOperation shouldBe JFrame.EXIT_ON_CLOSE

                val cameraSize = window.camera.screenRange
                cameraSize.width shouldBe 800
                cameraSize.height shouldBe 600
            }
            "load scene"{
                val factory = WindowFactoryImpl()
                val window = factory.build()

                window {
                    scene(::BaseScene)
                }

                (window.panel.scene is BaseScene) shouldBe true
            }
        }

    }

}
