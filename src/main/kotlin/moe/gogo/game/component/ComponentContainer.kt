package moe.gogo.game.component

import moe.gogo.game.utils.ClassMap
import kotlin.reflect.KClass


abstract class ComponentContainer {

    private val components: ClassMap<Component> = ClassMap()

    fun has(key: KClass<out Component>) = components.containsKey(key)
    inline fun <reified T : Component> has() = has(T::class)
    operator fun contains(key: KClass<out Component>) = has(key)

    operator fun <T : Component> get(key: KClass<T>): T = components.getWithCast(key)!!
    inline fun <reified T : Component> get() = get(T::class)

    fun getOrNull(key: KClass<out Component>) = components[key]
    inline fun <reified T : Component> getOrNull() = getOrNull(T::class)

    operator fun <T : Component> set(key: KClass<T>, component: T) {
        val old = components[key]
        val notAdded = old != component
        if (notAdded) {
            putAndInitComponent(key, component)
            replaceAndDestoryOldComponent(component, old)
        }
    }

    inline fun <reified T : Component> set(component: T) = set(T::class, component)


    private fun <T : Component> putAndInitComponent(key: KClass<T>, component: T) {
        val notAddedOrAddedOnThis = component.container == null || component.container == this
        check(notAddedOrAddedOnThis, { "$component has been add on other container" })
        components[key] = component
        component.init(this)
    }

    private fun <T : Component> replaceAndDestoryOldComponent(new: T, old: T?) {
        old?.let {
            new.replace(it)
            it.destroy()
        }
    }
}