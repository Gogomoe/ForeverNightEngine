package moe.gogo.game.behavior

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.test.createEmptyObject

class PositionBehaviorTest : StringSpec() {

    init {
        "update position when add parent"{
            val parent = createEmptyObject()
            val child = createEmptyObject()
            val position = Point(5, 0)
            val relative = Point(3, 4)
            val absolute = position + relative.toVector()

            parent.position.point = position
            child.position.point = absolute
            child.absolutePosition.point shouldBe absolute
            parent.add(child)
            child.absolutePosition.point shouldBe absolute
            child.position.point shouldBe relative
        }
        "update position when remove parent"{
            val parent = createEmptyObject()
            val child = createEmptyObject()
            val position = Point(5, 0)
            val relative = Point(3, 4)
            val absolute = position + relative.toVector()

            parent.add(child)
            parent.absolutePosition.point = position
            child.absolutePosition.point = absolute
            parent.remove(child)

            child.absolutePosition.point shouldBe absolute
            child.position.point shouldBe absolute
        }
    }
}