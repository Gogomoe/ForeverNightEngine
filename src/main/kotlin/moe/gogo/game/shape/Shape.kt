package moe.gogo.game.shape

import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.Point

/**
 * 形状的接口
 *
 * 形状依赖于[位置][position]
 */
abstract class Shape {

    open val position: Point = EMPTY_POINT

    val x
        get() = position.x
    val y
        get() = position.y

    /**
     * 点是否位于形状中
     */
    abstract operator fun contains(point: Point): Boolean

    //TODO 添加默认的实现方式
    /**
     * 与其他形状是否相接处
     */
    open fun contact(other: Shape): Boolean = boundingRect().contact(other)

    /**
     * 包围此形状的矩形
     */
    abstract fun boundingRect(): Rect

    abstract fun shiftTo(point: Point): Shape
}