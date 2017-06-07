package moe.gogo.game

import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses

typealias Observers<T> = MutableSet<Observer<T>>

class EventDispenser {

    private val observersOfAllTypes: MutableMap<KClass<*>, Observers<*>> = mutableMapOf()

    /**
     * 为指定类型的事件添加观察者
     */
    fun <E : Any> subscribe(type: KClass<E>, observer: Observer<E>): Observer<E> = observer.also {
        getOrCreateObserversByClass(type).add(observer)
    }

    fun <E : Any> subscribe(type: KClass<E>, observer: (E) -> Unit): Observer<E>
            = subscribe(type, Observer(observer))

    inline fun <reified E : Any> subscribe(noinline observer: (E) -> Unit): Observer<E>
            = subscribe(E::class, observer)

    private fun <E : Any> getOrCreateObserversByClass(type: KClass<E>): Observers<E> {
        return getObserversByClass(type) ?: createObserversByClass(type)
    }

    @Suppress
    private fun <E : Any> getObserversByClass(type: KClass<E>): Observers<E>? = observersOfAllTypes[type] as Observers<E>?

    @Suppress
    private fun <E : Any> createObserversByClass(type: KClass<E>): Observers<E> = mutableSetOf<Observer<E>>().also {
        observersOfAllTypes[type] = it as Observers<*>
    }

    /**
     * 触发指定类型的事件
     * 父类的观察者也会受到相关事件
     * 例如发送 Int 类型的事件, Number 的观察者也会收到该事件
     */
    fun <E : Any> emit(type: KClass<E>, event: E): E = event.also {
        collectSuperclassObservers(type).forEach { it(event) }
    }

    inline fun <reified E : Any> emit(event: E) = emit(E::class, event)


    private fun <E : Any> collectSuperclassObservers(type: KClass<E>): Observers<Any> {
        val observers: Observers<Any> = mutableSetOf()
        mergeSuperclassObservers(type.allSuperclasses, observers)
        mergeObservers(getObserversByClass(type), observers)
        return observers
    }

    private fun mergeSuperclassObservers(superclasses: Collection<KClass<*>>, observers: Observers<Any>) {
        superclasses
                .map { getObserversByClass(it) }
                .filterNotNull()
                .forEach {
                    mergeObservers(it, observers)
                }
    }

    @Suppress
    private fun mergeObservers(from: MutableSet<*>?, to: Observers<Any>) {
        from?.let { to.addAll(from as Observers<Any>) }
    }

    /**
     * 触发指定类型的事件
     * 与[emit]不同，这个方法不会触发父类型的观察者
     */
    fun <E : Any> emitOnlyCurrentClass(type: KClass<E>, event: E): E = event.also {
        getObserversByClass(type)?.forEach { it(event) }
    }

    inline fun <reified E : Any> emitOnlyCurrentClass(event: E) = emitOnlyCurrentClass(E::class, event)


    fun <E : Any> removeSubscriber(type: KClass<E>, observer: Observer<E>) {
        getObserversByClass(type)?.remove(observer)
    }

    inline fun <reified E : Any> removeSubscriber(observer: Observer<E>) = removeSubscriber(E::class, observer)


}