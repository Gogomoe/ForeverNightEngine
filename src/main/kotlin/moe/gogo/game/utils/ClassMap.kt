package moe.gogo.game.utils

import kotlin.collections.MutableMap.MutableEntry
import kotlin.reflect.KClass


class ClassMap<T : Any> : MutableMap<KClass<out T>, T> {

    private val map = mutableMapOf<KClass<out T>, T>()

    override val size: Int
        get() = map.size

    override fun containsKey(key: KClass<out T>) = map.containsKey(key)
    inline fun <reified E : T> containsKey() = containsKey(E::class)

    override fun containsValue(value: T) = map.containsValue(value)

    override fun get(key: KClass<out T>) = map[key]
    fun <E : T> getWithCast(key: KClass<E>): E? = map[key] as E?
    inline fun <reified E : T> get() = get(E::class)

    override fun isEmpty() = map.isEmpty()

    override val entries: MutableSet<MutableEntry<KClass<out T>, T>>
        get() = map.entries

    override val keys: MutableSet<KClass<out T>>
        get() = map.keys

    override val values: MutableCollection<T>
        get() = map.values

    override fun clear() = map.clear()

    override fun put(key: KClass<out T>, value: T): T? = map.put(key, value)
    inline fun <reified E : T> put(value: E) = put(E::class, value)

    override fun putAll(from: Map<out KClass<out T>, T>) = map.putAll(from)

    override fun remove(key: KClass<out T>): T? = map.remove(key)
    inline fun <reified E : T> remove() = remove(E::class)

}
