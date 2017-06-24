package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec


class DelegatedPropertyTest : StringSpec() {

    init {
        "lazyVar"{
            var v: Int by lazyVar { 5 }
            v shouldBe 5
            v = 10
            v shouldBe 10
        }
    }
}