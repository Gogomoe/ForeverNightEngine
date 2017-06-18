package moe.gogo.game.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <R> lazyVar(lazy: () -> R) = object : ReadWriteProperty<Any, R> {

    private var value: R? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): R {
        if (value == null) {
            value = lazy()
        }
        return value!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: R) {
        this.value = value
    }

}