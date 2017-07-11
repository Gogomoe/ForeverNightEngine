package moe.gogo.game.shape

import moe.gogo.game.utils.Point

/**
 * 形状的接口
 *
 * 形状依赖于[位置][position]
 */
interface Shape {

    val position: Point

    val x
        get() = position.x
    val y
        get() = position.y

    /**
     * 点是否位于形状中
     */
    operator fun contains(point: Point): Boolean

    //TODO 添加默认的实现方式
    /**
     * 与其他形状是否相接处
     */
    fun contact(other: Shape): Boolean = boundingRect().contact(other)

    /**
     * 包围此形状的矩形
     */
    fun boundingRect(): Rect

    fun shiftTo(point: Point): Shape
}