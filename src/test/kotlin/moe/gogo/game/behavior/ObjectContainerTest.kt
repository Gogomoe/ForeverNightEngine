package moe.gogo.game.behavior

import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import moe.gogo.game.FObject
import moe.gogo.test.`in`
import moe.gogo.test.createEmptyObject

class ObjectContainerTest : StringSpec() {

    init {
        "add object"{
            val (obj, container) = createObjectAndContainer()
            val child = createEmptyObject()

            container.add(child)

            container.children().size shouldBe obj.children().size
            container.children().size shouldBe 1
            child should `in`(container.children())
            child should `in`(obj.children())
            child.parent shouldBe obj
        }
        "remove objet"{
            val (obj, container) = createObjectAndContainer()
            val child = createEmptyObject()

            container.add(child)
            container.remove(child)

            container.children() should beEmpty()
        }
    }

    fun createObjectAndContainer(): Pair<FObject, Container> = createEmptyObject().let {
        Pair(it, it.container)
    }


}