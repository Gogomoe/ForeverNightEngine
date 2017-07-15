package moe.gogo.game.component

import moe.gogo.game.view.UIComponent

/**
 * 维护[UIComponent]的树形结构，使得UIComponent可以被添加到另一个UIComponent上
 */
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
        parent?.treeComponent?.children?.remove(UIComponent)
    }

    private fun bindNewParent(value: UIComponent?) {
        value?.treeComponent?.children?.add(UIComponent)
    }


    open var children: MutableSet<UIComponent> = mutableSetOf()
        protected set

}