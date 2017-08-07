package moe.gogo.game

typealias Observer<T> = (T)->Unit

class Subscriber<T> {

    private val observers: MutableSet<Observer<T>> = mutableSetOf()

    fun subscribe(observer: Observer<T>): Observer<T> {
        observers.add(observer)
        return observer
    }

    fun removeSubscriber(observer: Observer<T>): Unit {
        observers.remove(observer)
    }

    fun emit(event: T): Unit {
        observers.forEach { it(event) }
    }

}

fun Subscriber<Unit>.emit() = this.emit(Unit)
