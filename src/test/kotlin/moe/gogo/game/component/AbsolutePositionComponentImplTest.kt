package moe.gogo.game.component

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.view.UIComponent
import moe.gogo.test.generatePoint

class AbsolutePositionComponentImplTest : StringSpec() {

    init {
        "absolute position without parent"{
            val container = createContainer()
            val position = container[AbsolutePositionComponent::class]
            container.position = generatePoint()

            container.absolutePosition shouldBe container.position
            container.absolutePosition shouldBe position.position
        }
        "absolute position with parent"{
            val parent = createContainer()
            val container = createContainer()
            container.treeComponent.parent = parent

            parent.position = generatePoint()
            container.position = generatePoint()

            container.absolutePosition shouldBe parent.position + container.position.toVector()
        }
        "update relative position when change parent"{
            val parent = createContainer()
            val container = createContainer()
            container.treeComponent.parent = parent

            parent.position = generatePoint()
            container.position = generatePoint()

            var absolute = container.absolutePosition

            // sync absolute position
            container.absolutePositionComponent.sync {
                container.treeComponent.parent = null
            }
            parent.position = generatePoint()
            container.absolutePosition shouldBe absolute

            absolute = generatePoint()
            container.absolutePosition = absolute

            val newParent = createContainer()
            container.absolutePositionComponent.sync {
                container.treeComponent.parent = newParent
            }
            container.absolutePosition shouldBe absolute
        }
    }

    fun createContainer() = object : UIComponent() {}

}