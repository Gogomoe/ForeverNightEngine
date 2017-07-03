package moe.gogo.game.utils

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.test.`in`

class ClassMapTest : StringSpec() {

    init {
        "put"{
            val map = createNumberClassMap()
            val numbers: Set<Number> = generateAllTypeNumbers()
            numbers.forEach { map.put(it::class, it) }

            map.size shouldBe numbers.size
            numbers.forEach {
                map[it::class] shouldBe it
            }
        }

        "put and cover value"{
            val map = createNumberClassMap()
            val i: Int = Gen.int().generate()
            (1..10).forEach { map.put(Gen.int().generate()) }
            map.put(i)

            map.size shouldBe 1
            map.get<Int>() shouldBe i
        }

        "remove and contains key"{
            val map = createNumberClassMap()
            val numbers: Set<Number> = generateAllTypeNumbers()
            numbers.forEach { map.put(it::class, it) }

            map.remove<Double>()
            map.size shouldBe 3
            map.containsKey<Double>() shouldBe false

            map.clear()
            map.isEmpty() shouldBe true
        }
        "keys, values and entries"{
            val map = createNumberClassMap()
            val numbers: Set<Number> = generateAllTypeNumbers()
            numbers.forEach { map.put(it::class, it) }

            numbers.forEach {
                it::class shouldBe `in`(map.keys)
                it shouldBe `in`(map.values)
            }

            map.entries.forEach { (_, v) ->
                v shouldBe `in`(numbers)
            }
        }
        "put all values"{
            val map = createNumberClassMap()
            val empty = createNumberClassMap()
            val numbers: Set<Number> = generateAllTypeNumbers()
            numbers.forEach { map.put(it::class, it) }

            empty.putAll(map)

            numbers.forEach {
                empty.containsValue(it)
            }
        }
        "get with cast"{
            val map = createNumberClassMap()
            val double: Double = Gen.double().generate()
            map.put(double)

            val d: Double = map.getWithCast(Double::class)!!
            d shouldBe double
        }
    }

    fun createNumberClassMap() = ClassMap<Number>()
    fun generateAllTypeNumbers(): Set<Number> {
        val i: Int = Gen.int().generate()
        val f: Float = Gen.float().generate()
        val d: Double = Gen.double().generate()
        val l: Long = Gen.long().generate()
        return setOf(i, f, d, l)
    }
}