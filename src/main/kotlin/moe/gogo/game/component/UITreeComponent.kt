package moe.gogo.game.component

import moe.gogo.game.view.UIComponent

/**
 * 维护[UIComponent]的树形结构，使得UIComponent可以被添加到另一个UIComponent上
 *
 * 被添加的父级位置改变时，会带动子级位置改变
 *
 * 例如，文字可以被添加到图片上，图片可以添加到按钮上，
 * 当按钮位置改变时，文字、图片的位置会随之改变
 */
open class UITreeComponent : Component() {

    val UIComponent: UIComponent
        get() = container as UIComponent

    /**
     * 容器的父级，若该容器没有被添加到任何父容器上，则为null
     */
    open var parent: UIComponent? = null
        set(value) {
            if (value != parent) {
                removeOldParent()
                bindNewParent(value)
                field = value
            }
        }

    /**
     * 被添加到该容器的子级集合
     */
    open var children: MutableSet<UIComponent> = mutableSetOf()
        protected set

    private fun removeOldParent() {
        parent?.treeComponent?.children?.remove(UIComponent)
    }

    private fun bindNewParent(value: UIComponent?) {
        value?.treeComponent?.children?.add(UIComponent)
    }




}