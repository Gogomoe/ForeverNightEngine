package moe.gogo.game.component

import moe.gogo.game.view.UIComponent


open class UITreeComponent : Component() {

    val UIComponent: UIComponent
        get() = container as UIComponent

    open var parent: UIComponent? = null
        set(value) {
            if (value != parent) {
                removeOldParent()
                bindNewParent(value)
                field = value
            }
        }


    private fun removeOldParent() {
        parent?.let {
            it.treeComponent.children.remove(UIComponent)
        }
    }

    private fun bindNewParent(value: UIComponent?) {
        value?.let {
            it.treeComponent.children.add(UIComponent)
        }
    }


    open var children: MutableSet<UIComponent> = mutableSetOf()
        protected set


}