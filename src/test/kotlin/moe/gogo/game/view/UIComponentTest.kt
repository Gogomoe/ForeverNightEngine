package moe.gogo.game.view

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.utils.Point
import moe.gogo.test.equal
import moe.gogo.test.generateXY

class UIComponentTest : StringSpec() {

    init {
        "get and set x and y"{
            val component = createContainer()
            val (x, y) = generateXY()
            component.x = x
            component.y = y

            component.x should equal(x)
            component.y should equal(y)
            component.position shouldBe Point(x, y)
        }
        "contain components"{
            val component = createContainer()
            component.positionComponent shouldNotBe null
            component.absolutePositionComponent shouldNotBe null
            component.treeComponent shouldNotBe null
            component.renderComponent shouldNotBe null
        }
    }

    fun createContainer() = object : UIComponent() {}

}