import moe.gogo.game.component.MouseEventComponent
import moe.gogo.game.input.MouseEvent
import moe.gogo.game.utils.center
import moe.gogo.game.view.BaseScene
import moe.gogo.game.view.Text
import moe.gogo.game.view.UILayer
import moe.gogo.game.view.WindowFactoryImpl
import java.awt.Font
import java.awt.Font.PLAIN

fun main(args: Array<String>) {
    val factory = WindowFactoryImpl()
    val window = factory.build()

    window {
        title = "ForeverNightEngine"
        size(800, 600)
        center()
        scene(::BaseScene) {
            layer(::UILayer) {
                add(Text()) {
                    text = "Hello World!"
                    x = 400f
                    y = 260f
                    font = Font("Microsoft YaHei Light", PLAIN, 60)
                    set<MouseEventComponent>(object : MouseEventComponent() {
                        override fun handleClick(event: MouseEvent) {
                            println("Hello World")
                        }
                    })
                }
            }
        }
        isVisible = true
    }
}