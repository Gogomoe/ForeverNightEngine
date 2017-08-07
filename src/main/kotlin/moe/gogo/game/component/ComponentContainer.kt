package moe.gogo.game.component

import moe.gogo.game.utils.ClassMap
import kotlin.reflect.KClass

/**
 * 采用Entity模式的组件容器，
 * 可以为容器添加组件以获得更多的行为
 *
 * 处于安全考虑，默认不支持移除组件的操作
 *
 * 在添加、替换容器的过程中，会触发组件的生命周期，例如[Component.init]等，详见[Component]
 * @see Component
 */
abstract class ComponentContainer {

    /**
     * 所有容器的集合，相当于 Map<KClass, Component?>
     */
    protected val components: ClassMap<Component> = ClassMap()

    /**
     * 容器是否包含某个类型组件
     * @param key 需要检验的组件类型
     * @return true表示包含该类型的组件，false不包含
     */
    fun has(key: KClass<out Component>) = components.containsKey(key)

    inline fun <reified T : Component> has() = has(T::class)
    operator fun contains(key: KClass<out Component>) = has(key)

    /**
     * 获取指定类型的组件,若该类型的组件不存在，会抛出[NullPointerException]
     * @see getOrNull
     * @param key 需要获取的组件类型
     * @return 该类型的组件
     * @throws NullPointerException 若该类型的组件不存在，会抛出该异常
     */
    operator fun <T : Component> get(key: KClass<T>): T = components.getWithCast(key)!!

    inline fun <reified T : Component> get() = get(T::class)

    /**
     * 获取指定类型的组件，若该类型的组件不存在，则返回null
     * @see get
     * @param key 需要获取的组件类型
     * @return 该类型的组件
     */
    fun <T : Component> getOrNull(key: KClass<T>): T? = components.getWithCast(key)

    inline fun <reified T : Component> getOrNull() = getOrNull(T::class)

    /**
     * 设置指定类型的组件，会触发组件的生命周期，新的组件会触发[Component.init]，
     * 若原本已存在组件，则会触发原组件的[Component.destroy]与新组件的[Component.replace]
     * @param key 需要设置的组件类型
     * @param component 设置的组件
     */
    operator fun <T : Component> set(key: KClass<T>, component: T) {
        val old = components[key]
        // 该组件原先没有被添加到本容器上
        val notAdded = old != component
        if (notAdded) {
            putAndInitComponent(key, component)
            replaceAndDestroyOldComponent(component, old)
        }
    }

    inline fun <reified T : Component> set(component: T) = set(T::class, component)


    private fun <T : Component> putAndInitComponent(key: KClass<T>, component: T) {
        val notAddedOrAddedOnThis = component.container == null || component.container == this
        check(notAddedOrAddedOnThis, { "$component has been add on other container" })
        components[key] = component
        component.init(this)
    }

    private fun <T : Component> replaceAndDestroyOldComponent(new: T, old: T?) {
        old?.let {
            new.replace(it)
            it.destroy()
        }
    }
}