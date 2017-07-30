package moe.gogo.game.view

import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec
import moe.gogo.game.input.MouseEventHandler
import moe.gogo.test.TestStatus
import moe.gogo.test.has
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseEvent.MOUSE_CLICKED
import java.awt.event.MouseEvent.MOUSE_DRAGGED
import java.awt.event.MouseEvent.MOUSE_ENTERED
import java.awt.event.MouseEvent.MOUSE_EXITED
import java.awt.event.MouseEvent.MOUSE_MOVED
import java.awt.event.MouseEvent.MOUSE_PRESSED
import java.awt.event.MouseEvent.MOUSE_RELEASED

class PanelFactoryImplTest : StringSpec() {

    init {
        "check mouse listener"{
            val factory: PanelFactory = createFactory()
            val state = TestStatus()
            val panel: Panel = factory.build()
            panel.load(listeningMouseEventScene(state))
            with(panel.mouseListeners.first() as MouseAdapter) {
                mouseClicked(buildEvent(panel, MOUSE_CLICKED))
                mousePressed(buildEvent(panel, MOUSE_PRESSED))
                mouseReleased(buildEvent(panel, MOUSE_RELEASED))
                mouseMoved(buildEvent(panel, MOUSE_MOVED))
                mouseEntered(buildEvent(panel, MOUSE_ENTERED))
                mouseDragged(buildEvent(panel, MOUSE_DRAGGED))
                mouseExited(buildEvent(panel, MOUSE_EXITED))
            }
            state should has(MOUSE_EVENT_COUNT, 7)
        }
    }

    private fun listeningMouseEventScene(state: TestStatus): Scene = object : BaseScene() {
        override val mouseEventHandler: MouseEventHandler = object : MouseEventHandler {
            override fun handle(event: moe.gogo.game.input.MouseEvent) {
                state.next(MOUSE_EVENT_COUNT)
            }
        }
    }

    private fun createFactory(): PanelFactory = PanelFactoryImpl()

    private fun buildEvent(panel: Panel, type: Int): MouseEvent
            = MouseEvent(panel, type, 0, 0, 0, 0, 0, false)


    private val MOUSE_EVENT_COUNT = "mouse event"
}