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

interface ReadOnlyDelegate<out R> {
    fun get(): R
    operator fun invoke() = get()
}

fun <R> ReadOnlyDelegate(getter: () -> R) = object : ReadOnlyDelegate<R> {

    override fun get(): R = getter()

}

interface ReadWriteDelegate<R> {
    fun get(): R
    fun set(value: R)
}

fun <R> ReadWriteDelegate(getter: () -> R, writer: (R) -> Unit) = object : ReadWriteDelegate<R> {

    override fun get(): R = getter()

    override fun set(value: R) = writer(value)

}