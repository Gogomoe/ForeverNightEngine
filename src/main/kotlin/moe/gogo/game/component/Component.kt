package moe.gogo.game.component

import kotlin.reflect.KClass

/**
 * 所有组件的超类
 *
 * 每个组件只能同时被添加在一个容器中。
 * 组件拥有生命周期，组件被添加到容器上时触发[init]，
 * 被移除时触发[destroy]，替换别的同类型的组件时触发[replace]
 *
 * 组件可以相互依赖，可以在[init]中使用[dependOn]方法检查容器是否拥有依赖项
 * @see ComponentContainer
 */
abstract class Component {

    /**
     * 组件被添加的容器
     */
    open var container: ComponentContainer? = null
        protected set

    /**
     * 组件被添加到容器时初始化操作
     * @param container 被添加到的容器
     */
    open fun init(container: ComponentContainer) {
        this.container = container
    }

    /**
     * 组件被移除时的销毁操作
     */
    open fun destroy() {
        this.container = null
    }

    /**
     * 组件替换掉另一个同类型的组件时的操作
     * @param other 被替换的另一个同类型的组件
     */
    open fun replace(other: Component) {}

    /**
     * 用于检验组件的依赖，通常在组件初始化[init]时进行检验
     *
     * 若容器没有包含该类型的组件，则会抛出[IllegalStateException]，
     * 有则返回该组件
     * @param clazz 依赖的组件的类型
     * @return 该类型的组件
     * @throws IllegalStateException 若容器没有包含该类型的组件，则会抛出异常
     */
    protected fun <T : Component> dependOn(clazz: KClass<T>): T {
        check(container != null, { "${this::class.simpleName} did not initialized" })
        check(container?.has(clazz)!!, { "${this::class.simpleName} depend on ${clazz.simpleName}" })
        return container!![clazz]
    }

    protected inline fun <reified T : Component> dependOn() = dependOn(T::class)

}