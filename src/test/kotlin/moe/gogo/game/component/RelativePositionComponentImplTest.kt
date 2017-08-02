package moe.gogo.game.component

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.view.UIComponent
import moe.gogo.test.generatePoint

class RelativePositionComponentImplTest : StringSpec() {

    init {
        "relative position without parent"{
            val container = createContainer()
            val position = container[RelativePositionComponent::class]
            container.position = generatePoint()

            container.relativePosition shouldBe container.position
            container.relativePosition shouldBe position.position
        }
        "relative position with parent"{
            val parent = createContainer()
            val container = createContainer()
            container.treeComponent.parent = parent

            parent.position = generatePoint()
            container.position = generatePoint()

            container.relativePosition shouldBe container.position - parent.position.toVector()
        }
        "update relative position when change parent"{
            val parent = createContainer()
            val container = createContainer()
            container.treeComponent.parent = parent

            parent.position = generatePoint()
            container.position = generatePoint()

            container.relativePosition shouldBe container.position - parent.position.toVector()

            container.treeComponent.parent = null
            container.relativePosition shouldBe container.position

            val newParent = createContainer()
            container.treeComponent.parent = newParent

            container.relativePosition shouldBe container.position - newParent.position.toVector()
        }
    }

    fun createContainer() = object : UIComponent() {}

}