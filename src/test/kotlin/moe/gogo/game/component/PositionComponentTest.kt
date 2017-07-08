package moe.gogo.game.component

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import moe.gogo.test.TestStatus
import moe.gogo.test.generatePoint

class PositionComponentTest : StringSpec() {

    init {
        "get and set position"{
            val position = PositionComponent()
            val point = generatePoint()

            position.position = point
            position.position shouldBe point
        }
        "subscribe"{
            val position = PositionComponent()
            val status = TestStatus()
            position.onChange { status.next() }

            position.onChangeSubscriber() shouldNotBe null
            for (i in 1..10) {
                val point = generatePoint()
                position.position = point
            }

            status shouldBigger 8

            //相同时不设置，不触发改变
            val state = status.value
            position.position = position.position
            status shouldBe state
        }

    }

    fun createPositionComponent() = PositionComponent().also {
        val container = createContainer()
        container[PositionComponent::class] = it
    }

}