package moe.gogo.game

class Subscriber<T> {

    private val observers: MutableSet<Observer<T>> = mutableSetOf()

    fun subscribe(observer: (T) -> Unit): Observer<T> = subscribe(Observer(observer))

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
