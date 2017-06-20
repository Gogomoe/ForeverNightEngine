package moe.gogo.game.behavior

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.test.createEmptyObject

class PositionPropertyTest : StringSpec() {

    init {
        "update position when add parent"{
            val parent = createEmptyObject()
            val child = createEmptyObject()
            val position = Point(5, 0)
            val relative = Point(3, 4)
            val absolute = position + relative.toVector()

            parent.position = position
            child.position = absolute
            child.absolutePosition shouldBe absolute
            parent.add(child)
            child.absolutePosition shouldBe absolute
            child.position shouldBe relative
        }
        "update position when remove parent"{
            val parent = createEmptyObject()
            val child = createEmptyObject()
            val position = Point(5, 0)
            val relative = Point(3, 4)
            val absolute = position + relative.toVector()

            parent.add(child)
            parent.absolutePosition = position
            child.absolutePosition = absolute
            parent.remove(child)

            child.absolutePosition shouldBe absolute
            child.position shouldBe absolute
        }
    }
}