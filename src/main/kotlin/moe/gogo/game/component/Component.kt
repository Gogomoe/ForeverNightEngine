package moe.gogo.game.component


abstract class Component {

    var container: ComponentContainer? = null
        protected set

    open fun init(container: ComponentContainer) {
        this.container = container
    }

    open fun destroy() {
        this.container = null
    }

    open fun replace(other: Component) {}

}