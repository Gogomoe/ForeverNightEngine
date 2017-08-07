package moe.gogo.game.shape

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import kotlin.reflect.KClass

/**
 * 管理[Collider]的注册表，实现了[Collider]后需要被添加到本对象上
 *
 * @see Shape
 * @see Collider
 */
object ColliderRegistry {

    /**
     * 两个键对应一个值的集合，两个形状类型对应着该类型的碰撞器
     */
    val table: Table<KClass<out Shape>, KClass<out Shape>, Collider<*, *>> = HashBasedTable.create()

    /**
     * 注册一个碰撞器
     * @param c1 类型1
     * @param c2 类型2
     * @param collider 需要被注册的碰撞器
     */
    fun <T : Shape, S : Shape> register(c1: KClass<out T>, c2: KClass<out S>, collider: Collider<T, S>) {
        table.put(c1, c2, collider)
    }

    inline fun <reified T : Shape, reified S : Shape> register(collider: Collider<T, S>)
            = register(T::class, S::class, collider)

    /**
     * 注销指定类型的碰撞器
     * @param c1 类型1
     * @param c2 类型2
     * @return 被注销的碰撞器，若没有碰撞器被注销，则为null
     */
    fun <T : Shape, S : Shape> unregister(c1: KClass<out T>, c2: KClass<out S>): Collider<T, S>? {
        return table.remove(c1, c2) as Collider<T, S>?
    }

    inline fun <reified T : Shape, reified S : Shape> unregister(): Collider<T, S>?
            = unregister(T::class, S::class)

    /**
     * 得到指定类型的碰撞器
     * @param c1 类型1
     * @param c2 类型2
     * @return 指定类型的碰撞器，若没有指定类型的碰撞器，则为null
     */
    fun <T : Shape, S : Shape> get(c1: KClass<out T>, c2: KClass<out S>): Collider<T, S>?
            = table.get(c1, c2) as Collider<T, S>?

    inline fun <reified T : Shape, reified S : Shape> get(): Collider<T, S>?
            = get(T::class, S::class)
}