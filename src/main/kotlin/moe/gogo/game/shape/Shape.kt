package moe.gogo.game.shape

import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point
import kotlin.reflect.KClass

/**
 * 形状
 *
 * 形状拥有[位置][position]
 *
 * 默认的[contact]方法使用[ColliderRegistry]获取碰撞器，
 * 若没有改类型的碰撞器，则会使用[boundingRect]检验形状接触
 *
 * 若需要扩充一个形状，除了实现[contains]方法之外，最好实现[Collider]用于判断新形状于其他形状的接触。
 *
 * @see Collider
 * @see ColliderRegistry
 */
abstract class Shape {

    open val position: Point = EMPTY_POINT

    val x
        get() = position.x
    val y
        get() = position.y

    /**
     * 点是否位于形状中
     * @param point 需要判断的点
     * @return 为true则代表该点在形状中
     */
    abstract operator fun contains(point: Point): Boolean

    /**
     * 与其他形状是否相接处
     * @param other 需要判断的形状
     * @return 为true则代表该两个形状有接触部分
     * @see ColliderRegistry
     */
    open fun contact(other: Shape): Boolean
            = contactByCollider(this::class, other::class, this, other) ?:
            contactByCollider(other::class, this::class, other, this) ?:
            boundingRect().contact(other.boundingRect())

    private fun <T : Shape, S : Shape> contactByCollider(c1: KClass<out T>, c2: KClass<out S>, t: T, s: S): Boolean?
            = ColliderRegistry.get(c1, c2)?.contact(t, s)


    /**
     * 包围此形状的矩形
     * @return 包围此形状的矩形
     */
    abstract fun boundingRect(): Rect

    /**
     * 移动本形状到新的位置
     * @param point 新的位置
     * @return 移动后的形状
     */
    abstract fun shiftTo(point: Point): Shape
}