package moe.gogo.game.event

/**
 * 事件
 *
 * 事件可以被[消费][consume]，已经被消费的事件通常不会继续使用
 */
abstract class Event {

    /**
     * 事件是否被消费
     */
    var consumed: Boolean = false

    /**
     * 消费此事件
     */
    open fun consume() {
        consumed = true
    }

}