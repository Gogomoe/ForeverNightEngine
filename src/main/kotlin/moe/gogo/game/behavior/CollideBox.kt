package moe.gogo.game.behavior

import moe.gogo.game.FObject
import moe.gogo.game.Subscriber

/**
 * CollideBox 表示可以碰撞的对象，可以与一个点或另一个 FObject 是否碰撞（重合）
 */
interface CollideBox {

    fun isCollide(other: FObject): Boolean

    fun containsPoint(point: Point): Boolean

}

/**
 * CollideBehavior 除了可以判断与另一个物体是否碰撞，还能发送、订阅碰撞事件
 */
abstract class CollideBehavior(val self: FObject) : CollideBox {

    open var subscriber: Subscriber<Pair<FObject, FObject>>? = null
        protected set

    open fun emit(other: FObject) = subscriber?.emit(Pair(self, other))

    open fun on(observer: (Pair<FObject, FObject>) -> Unit)
            = getOrCreateSubscriber().subscribe(observer)

    private fun getOrCreateSubscriber(): Subscriber<Pair<FObject, FObject>>
            = subscriber ?: Subscriber<Pair<FObject, FObject>>().also {
        subscriber = it
    }
}