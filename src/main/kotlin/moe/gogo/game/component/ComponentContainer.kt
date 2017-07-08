package moe.gogo.game.component

import moe.gogo.game.utils.ClassMap
import kotlin.reflect.KClass

/**
 * 采用Entity模式的组件容器，
 * 可以为容器添加组件以获得更多的行为
 *
 * 处于安全考虑，默认不支持移除组件的操作
 *
 * 在添加、替换容器的过程中，会触发组件的生命周期
 * @see Component
 */
abstract class ComponentContainer {

    protected val components: ClassMap<Component> = ClassMap()

    /**
     * 容器是否包含某个组件
     */
    fun has(key: KClass<out Component>) = components.containsKey(key)

    inline fun <reified T : Component> has() = has(T::class)
    operator fun contains(key: KClass<out Component>) = has(key)

    /**
     * 获取指定类型的组件
     */
    operator fun <T : Component> get(key: KClass<T>): T = components.getWithCast(key)!!

    inline fun <reified T : Component> get() = get(T::class)

    fun getOrNull(key: KClass<out Component>) = components[key]
    inline fun <reified T : Component> getOrNull() = getOrNull(T::class)

    /**
     * 设置指定类型的组件
     */
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