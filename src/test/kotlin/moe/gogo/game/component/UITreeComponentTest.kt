package moe.gogo.game.component

import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.view.UIComponent
import moe.gogo.test.`in`

class UITreeComponentTest : StringSpec() {

    init {
        "add parent"{
            val parent = createUIComponent()
            val container = createUIComponent()
            container.treeComponent.parent = parent

            container should `in`(parent.children)
        }
        "add parent repeatedly"{
            val parent = createUIComponent()
            val container = createUIComponent()
            container.treeComponent.parent = parent
            container.treeComponent.parent = parent

            container should `in`(parent.children)
            parent.children.size shouldBe 1
        }

        "remove parent"{
            val parent = createUIComponent()
            val container = createUIComponent()
            container.treeComponent.parent = parent

            container.treeComponent.parent = null
            parent.children should beEmpty<UIComponent>()
        }
    }

    fun createUIComponent(): UIComponent = object : UIComponent() {}
}