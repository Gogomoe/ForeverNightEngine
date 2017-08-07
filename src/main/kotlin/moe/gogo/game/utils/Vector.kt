package moe.gogo.game.utils

import java.lang.Math.pow
import java.lang.Math.sqrt

/**
 * Vector 表示一个向量，Vector的设计与[Point]差不多，因此建议先阅读[Point的文档][Point]
 *
 * 相对于Point，Vector扩充了一些功能，比如向量可以[乘][times]上指定倍数，可以计算[长度][length]
 *
 * @see Point
 *
 * @param x x轴坐标
 * @param y y轴坐标
 */
class Vector(val x: Float, val y: Float) {

    /**
     * @param x x轴坐标
     * @param y y轴坐标
     */
    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())

    /**
     * @param x 新的x值
     * @return 改变x轴坐标、y轴坐标不变的向量
     */
    fun setX(x: Number): Vector = Vector(x, y)

    /**
     * @param y 新的x值
     * @return 改变y轴坐标、x轴坐标不变的向量
     */
    fun setY(y: Number): Vector = Vector(x, y)

    /**
     * @param v 加上的向量
     * @return 这个向量加上向量后的新向量
     */
    operator fun plus(v: Vector): Vector = Vector(x + v.x, y + v.y)

    /**
     * @param v 减去的向量
     * @return 这个向量减去向量后的新向量
     */
    operator fun minus(v: Vector): Vector = Vector(x - v.x, y - v.y)

    /**
     * @return 该向量的长度
     */
    fun length(): Float = sqrt(pow(x.toDouble(), 2.0) + pow(y.toDouble(), 2.0)).toFloat()

    /**
     * @param times 乘上的倍数
     * @return 该向量乘上指定倍数后的新向量
     */
    operator fun times(times: Number): Vector = Vector(x * times.toFloat(), y * times.toFloat())

    /**
     * 转化成x、y相等的[Point]
     * @return x、y相等的Point
     */
    fun toPoint(): Point = Point(x, y)

    /**
     * Vector的equals方法存在与Point同样的问题，因此建议读Point的相关条目
     * @see Point.equals
     */
    override fun equals(other: Any?): Boolean = when (other) {
        is Vector -> {
            x equal other.x && y equal other.y && hashCode() == other.hashCode()
        }
        else -> false
    }

    operator fun component1(): Float = x
    operator fun component2(): Float = y

    /**
     * @return 转换成 Int 类型的x、y坐标，没有四舍五入
     */
    fun int() = Pair(x.toInt(), y.toInt())

    override fun hashCode(): Int = (x * 0.1).toInt() * 47 + (y * 0.1).toInt() * 29

    override fun toString(): String = "(%.1f, %.1f)".format(x, y)

}