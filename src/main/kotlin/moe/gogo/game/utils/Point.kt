package moe.gogo.game.utils

/**
 * 表示一个二维的点
 *
 * 点是不可变的，因此对点的操作会产生一个新的[Point]对象
 *
 * 点可以通过[toVector]与[向量][Vector]相互转化，这二者的区别是，
 * 向量拥有更多的运算（例如扩大倍数、求长度），而这些计算对点是没有意义的
 *
 * 点可以[加][plus]、[减][minus]一个向量，得到一个新的点
 *
 * 由于对精度要求不高，并且为了避免浮点数运算误差，
 * 因此两个点的横纵坐标之差都在 0.1 以内，就可以认为是同一个点
 *
 * @see Vector
 *
 * @param x x轴坐标
 * @param y y轴坐标
 */
class Point(val x: Float, val y: Float) {

    /**
     * @param x x轴坐标
     * @param y y轴坐标
     */
    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())

    /**
     * @param x 新的x值
     * @return 改变x轴坐标、y轴坐标不变的点
     */
    fun setX(x: Number): Point = Point(x, y)

    /**
     * @param y 新的x值
     * @return 改变y轴坐标、x轴坐标不变的点
     */
    fun setY(y: Number): Point = Point(x, y)

    /**
     * @param v 加上的向量
     * @return 这个点加上向量后的新点
     */
    operator fun plus(v: Vector): Point = Point(x + v.x, y + v.y)

    /**
     * @param v 减去的向量
     * @return 这个点减去向量后的新点
     */
    operator fun minus(v: Vector): Point = Point(x - v.x, y - v.y)

    /**
     * 转化成x、y相等的[Vector]
     * @return x、y相等的Vector
     */
    fun toVector(): Vector = Vector(x, y)

    /**
     * 判断两个点是否相等
     *
     * 由于对精度要求不高，并且为了避免浮点数运算误差，
     * 因此两个点的横纵坐标之差都在 0.1 以内，就可以认为是同一个点
     *
     * 值得注意的是，这个方法覆盖并不规范，它违反了equals的传递性原则，
     * 可能出现 `a == b` , `b == c `, 然而 `a != c` 的情况
     *
     * 不过这种情况极少出现，绝大多是情况下两个相等的点误差不超过 0.01，
     * 因此即使不规范，仍可以接受
     */
    override fun equals(other: Any?): Boolean = when (other) {
        is Point -> {
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