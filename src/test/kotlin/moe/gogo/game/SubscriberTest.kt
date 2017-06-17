package moe.gogo.game

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import moe.gogo.test.TestStatus

class SubscriberTest : StringSpec() {

    init {
        "receive event"{
            val (subscriber, status) = createSubscriberAndStatus<String>()
            subscriber.subscribe {
                status.next()
            }

            subscriber.emit(Gen.string().generate())
            status shouldBe 1
        }
        "receive correct args"{
            val subscriber = createSubscriber<String>()
            var arg = ""
            subscriber.subscribe {
                it shouldBe arg
            }
            for (i in 1..10) {
                arg = Gen.string().generate()
                subscriber.emit(arg)
            }
        }
        "remove subscriber"{
            val (subscriber, status) = createSubscriberAndStatus<String>()
            var observer: Observer<String> = Observer { }
            observer = subscriber.subscribe {
                status.next()
                if (status.value == 5) {
                    subscriber.removeSubscriber(observer)
                }
            }
            for (i in 1..10) {
                subscriber.emit(Gen.string().generate())
            }
            status shouldBe 5
        }
        "no-params subscriber"{
            val (subscriber, status) = createSubscriberAndStatus<Unit>()
            subscriber.subscribe {
                status.next()
            }
            subscriber.emit()
            status shouldBe 1
        }
    }

    fun <T> createSubscriberAndStatus() = Pair(Subscriber<T>(), TestStatus())
    fun <T> createSubscriber() = Subscriber<T>()

}