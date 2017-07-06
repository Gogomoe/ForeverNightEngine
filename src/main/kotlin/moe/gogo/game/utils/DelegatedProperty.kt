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