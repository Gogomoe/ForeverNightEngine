package moe.gogo.game.component

/**
 * 所有组件的超类
 *
 * 每个组件只能同时被添加在一个容器中。
 * 组件拥有生命周期，组件被添加到容器上时触发[init]，
 * 被移除时触发[destroy]，替换别的同类型的组件时触发[replace]
 */
abstract class Component {

    var container: ComponentContainer? = null
        protected set

    /**
     * 组件被添加到容器时初始化操作
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
     */
    open fun replace(other: Component) {}

}