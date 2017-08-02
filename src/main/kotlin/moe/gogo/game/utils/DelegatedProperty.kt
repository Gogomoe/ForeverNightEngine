package moe.gogo.game.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <R> lazyVar(lazy: () -> R) = object : ReadWriteProperty<Any?, R> {

    private var value: R? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): R {
        if (value == null) {
            value = lazy()
        }
        return value!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: R) {
        this.value = value
    }

}

class LazyProperty<R>(private val initializer: () -> R) {

    private var initialized = false
    private var property: R? = null

    fun init() {
        if (notInitialized()) {
            property = initializer()
            initialized = true
        }
    }

    private fun notInitialized(): Boolean = !initialized && property == null

    fun get(): R {
        if (notInitialized()) {
            throw IllegalStateException("LazyProperty not initialized")
        }
        return property!!
    }

    operator fun invoke() = get()

    fun orNull(): R? = property

    fun set(value: R) = kotlin.run { property = value }

}

typealias ReadOnlyDelegate<T> = () -> T

interface ReadWriteDelegate<T> {
    fun get(): T
    fun set(value: T)
    operator fun invoke() = get()
    operator fun invoke(value: T) = set(value)
}

fun <T> ReadWriteDelegate(getter: () -> T, writer: (T) -> Unit) = object : ReadWriteDelegate<T> {

    override fun get(): T = getter()

    override fun set(value: T) = writer(value)

}