package moe.gogo.game.input

/**
 * 用于处理鼠标事件的接口
 * @see MouseEvent
 */
interface MouseEventHandler {

    /**
     * 处理鼠标事件
     */
    fun handle(event: MouseEvent): Unit

    operator fun invoke(event: MouseEvent) = handle(event)

}