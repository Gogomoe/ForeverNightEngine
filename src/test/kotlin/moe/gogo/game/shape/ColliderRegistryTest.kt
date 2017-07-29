package moe.gogo.game.shape

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class ColliderRegistryTest : StringSpec() {

    init {
        "register and unregister"{
            ColliderRegistry.get<ShapeA, ShapeB>() shouldBe null
            val collider = ABCollider()
            ColliderRegistry.register(collider)
            ColliderRegistry.get<ShapeA, ShapeB>() shouldBe collider
            ColliderRegistry.unregister<ShapeA, ShapeB>()
            ColliderRegistry.get<ShapeA, ShapeB>() shouldBe null
        }
    }

}