package moe.gogo.game.view

import moe.gogo.game.shape.Rect
import moe.gogo.game.utils.EMPTY_POINT
import moe.gogo.game.utils.EMPTY_RECT
import moe.gogo.game.utils.Point

/**
 * Camera 保存了屏幕大小以及需要绘制的游戏界面大小等信息
 */
abstract class Camera {

    open val screenRange: Rect = EMPTY_RECT
    open val position: Point = EMPTY_POINT
    open val gameRange: Rect = EMPTY_RECT

    abstract fun shiftTo(point: Point)

}