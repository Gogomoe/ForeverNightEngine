package moe.gogo.game.shape

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import kotlin.reflect.KClass

object ColliderRegistry {

    val table: Table<KClass<out Shape>, KClass<out Shape>, Collider<*, *>> = HashBasedTable.create()

    fun <T : Shape, S : Shape> register(c1: KClass<out T>, c2: KClass<out S>, collider: Collider<T, S>) {
        table.put(c1, c2, collider)
    }

    inline fun <reified T : Shape, reified S : Shape> register(collider: Collider<T, S>)
            = register(T::class, S::class, collider)

    fun <T : Shape, S : Shape> unregister(c1: KClass<out T>, c2: KClass<out S>): Collider<T, S>? {
        return table.remove(c1, c2) as Collider<T, S>?
    }

    inline fun <reified T : Shape, reified S : Shape> unregister(): Collider<T, S>?
            = unregister(T::class, S::class)

    fun <T : Shape, S : Shape> get(c1: KClass<out T>, c2: KClass<out S>): Collider<T, S>?
            = table.get(c1, c2) as Collider<T, S>?

    inline fun <reified T : Shape, reified S : Shape> get(): Collider<T, S>?
            = get(T::class, S::class)
}