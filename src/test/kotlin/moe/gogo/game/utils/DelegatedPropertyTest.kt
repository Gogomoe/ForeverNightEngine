package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec


class DelegatedPropertyTest : StringSpec() {

    init {
        "LazyProperty"{
            val str: LazyProperty<String> = LazyProperty { Gen.string().generate() }

            str.orNull() shouldBe null
            shouldThrow<IllegalStateException> {
                str()
            }
            str.init()
            str() shouldNotBe null

            str.init()
            str.set(Gen.string().generate())
            str() shouldNotBe null
        }

        "ReadOnlyDelegate"{
            val v: Int = Gen.int().generate()
            val f = { v }

            val delegate = f
            delegate() shouldBe f()
        }

        "ReadWriteDelegate"{
            var v = Gen.int().generate()
            val getter = { v }
            val setter = { value: Int -> v = value }

            val delegate = ReadWriteDelegate(getter, setter)
            delegate.get() shouldBe getter()
            delegate.set(Gen.int().generate())
            delegate.get() shouldBe getter()
        }
    }
}