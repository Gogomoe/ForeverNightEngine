package moe.gogo.game

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.test.TestStatus


class EventDispenserTest : StringSpec() {
    init {
        "dispense events without any observer"{
            val dispenser = createEventDispenser()
            dispenser.emit(Gen.string().generate())
        }
        "receive events"{
            val (dispenser, status) = createEventDispenserAndStatus()
            dispenser.subscribe<String> { status.next() }
            dispenser.subscribe(Number::class) { status.next(5) }
            dispenser.subscribe(Int::class, Observer { status.next(10) })

            dispenser.emit(Gen.string().generate())
            dispenser.emit(Gen.string().generate())
            dispenser.emit(Gen.int().generate())

            // 2 (String) + 5 (Number) + 10 (Int)
            status shouldBe 17
        }
        "only observer of current class could receive events"{
            val (dispenser, status) = createEventDispenserAndStatus()
            dispenser.subscribe(Number::class) { status.next(5) }
            dispenser.subscribe(Int::class, Observer { status.next(10) })

            dispenser.emitOnlyCurrentClass(Gen.int().generate())

            status shouldBe 10
        }
        "receive correct args"{
            val dispenser = createEventDispenser()
            var arg = ""
            dispenser.subscribe<String> {
                it shouldBe arg
            }
            for (i in 1..10) {
                arg = Gen.string().generate()
                dispenser.emit(arg)
            }
        }
        "remove subscriber"{
            val (dispenser, status) = createEventDispenserAndStatus()
            var observer: Observer<String> = Observer { }
            observer = dispenser.subscribe {
                status.next()
                if (status.value == 5) {
                    dispenser.removeSubscriber(observer)
                }
            }
            for (i in 1..10) {
                dispenser.emit(Gen.string().generate())
            }
            status shouldBe 5
        }
    }

    private fun createEventDispenserAndStatus() = Pair(EventDispenser(), TestStatus())
    private fun createEventDispenser() = EventDispenser()
}
