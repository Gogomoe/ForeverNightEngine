package moe.gogo.game.input

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.input.MouseEventType.CLICK
import moe.gogo.game.input.MouseEventType.ENTER
import moe.gogo.game.input.MouseEventType.EXIT
import moe.gogo.game.input.MouseEventType.MOVE
import moe.gogo.game.input.MouseEventType.PRESS
import moe.gogo.game.input.MouseEventType.RELEASE
import moe.gogo.game.input.SimpleMouseEventType.ACTION
import moe.gogo.game.utils.Point

class MouseEventConverterTest : StringSpec() {

    init {
        "simplify type"{
            with(createConverter()) {
                simplifyType(CLICK()) shouldBe ACTION
                simplifyType(PRESS()) shouldBe ACTION
                simplifyType(RELEASE()) shouldBe ACTION
                simplifyType(MOVE()) shouldBe SimpleMouseEventType.MOVE
                simplifyType(ENTER()) shouldBe SimpleMouseEventType.MOVE
                simplifyType(EXIT()) shouldBe SimpleMouseEventType.MOVE
            }
        }
        "convert type"{
            with(createConverter()) {
                convert(createEvent(), true, true)!!.type shouldBe MOVE
                convert(createEvent(), true, false)!!.type shouldBe ENTER
                convert(createEvent(), false, true)!!.type shouldBe EXIT
                convert(createEvent(), false, false) shouldBe null
            }
        }
        "consume converted event type"{
            with(createConverter()) {
                val e1 = createEvent()
                val e2 = createEvent()
                val converted1 = convert(e1, true, true)!!
                val converted2 = buildWithType(e2, CLICK)
                converted1.consume()
                converted2.consume()
                e1.consumed shouldBe true
                e2.consumed shouldBe true
            }
        }
    }

    private fun createConverter() = MouseEventConverter()

    private fun createEvent(x: Number = 0, y: Number = 0, type: MouseEventType = CLICK)
            = MouseEvent(Point(x, y), type)

    private operator fun MouseEventType.invoke() = createEvent(type = this)
}