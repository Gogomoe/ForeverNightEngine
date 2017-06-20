package moe.gogo.game.behavior

import moe.gogo.game.FObject

/**
 * FObject 可以被添加到 Container 中
 *
 * 可以用来组合 FObject ，表达层次关系
 * 例如 船长站在船上，船移动时，船长的也随之移动
 *
 * 另一种使用方法是作为容器，渲染Scene时会渲染它所有的children
 */
interface Container {

    fun add(obj: FObject): Boolean

    fun remove(obj: FObject): Boolean

    fun children(): Collection<FObject>
}

/**
 * 用set储存子元素的Container
 */
open class SetContainer(open val self: Container) : Container {

    val children: MutableSet<FObject> = mutableSetOf()

    override fun add(obj: FObject): Boolean {
        return children.add(obj)
    }

    override fun remove(obj: FObject): Boolean {
        obj.parent = null
        return children.remove(obj)
    }

    override fun children(): Collection<FObject> = children

}

/**
 * 用于 FObject 的Container，会在添加或移除元素时，更新该元素的相对位置，保持绝对位置不变
 */
class ObjectContainer(override val self: FObject) : SetContainer(self) {

    override fun add(obj: FObject): Boolean {
        if (obj.parent == self) {
            return true
        }
        updatePosition(obj) {
            obj.parent?.remove(obj)
            obj.parent = self
        }
        return super.add(obj)
    }

    override fun remove(obj: FObject): Boolean {
        updatePosition(obj) {
            obj.parent = null
        }
        return super.remove(obj)
    }

    private inline fun updatePosition(obj: FObject, body: () -> Unit) {
        val absolutePosition = obj.absolutePosition
        body()
        obj.absolutePosition = absolutePosition
    }
}