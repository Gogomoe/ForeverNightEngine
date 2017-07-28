package moe.gogo.game.component

import moe.gogo.game.shape.Rect
import moe.gogo.game.shape.Shape
import moe.gogo.game.utils.Point

/**
 * 具有一定形状的组件，用于产生碰撞、点击等当面
 *
 * 注意，获取的shape对象的position 应该与 container.position相同，否则可能会造成位置错误。
 * 可以使用 `shape.shiftTo(container.position)` 等方法修正位置
 */
abstract class ShapeComponent : Component() {

    /**
     * 对象的形状
     */
    abstract val shape: Shape

    /**
     * 某个点是否包含在对象的形状中
     */
    open operator fun contains(point: Point): Boolean = shape.contains(point)

    /**
     * 对象是否与另一个形状相接触
     */
    open fun contact(other: Shape): Boolean = shape.contact(other)

    /**
     * 包围此形状的矩形
     */
    open fun boundingRect(): Rect = shape.boundingRect()

}