package moe.gogo.game.view

import moe.gogo.game.shape.Rect
import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.EMPTY_RECT
import moe.gogo.game.utils.Point

/**
 * View 保存了屏幕大小以及需要绘制的游戏界面大小等信息
 */
open class View {

    open var screenRange: Rect = EMPTY_RECT
        set(value) {
            field = value.shiftTo(Point(value.width.toFloat() / 2, value.height.toFloat() / 2))
        }
    open var position: Point = EMPTY_POINT
    open var gameRange: Rect = EMPTY_RECT

}