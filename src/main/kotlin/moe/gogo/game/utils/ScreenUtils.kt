package moe.gogo.game.utils

import moe.gogo.game.shape.Rect
import moe.gogo.game.view.Window
import java.awt.Toolkit

/**
 * 获取屏幕大小
 * @return 屏幕大小
 */
fun screenSize(): Rect {
    val d = Toolkit.getDefaultToolkit().screenSize
    return Rect(d.width, d.height)
}

/**
 * 将窗口设为屏幕中心
 */
fun Window.center() {
    val w = this.width
    val h = this.height
    val (screenw, screenh) = screenSize()
    val x = (screenw - w) / 2
    val y = (screenh - h) / 2
    this.setLocation(x, y)
}