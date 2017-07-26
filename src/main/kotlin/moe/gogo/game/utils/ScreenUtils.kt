package moe.gogo.game.utils

import moe.gogo.game.shape.Rect
import moe.gogo.game.view.Window
import java.awt.Toolkit

fun screenSize(): Rect {
    val d = Toolkit.getDefaultToolkit().screenSize
    return Rect(d.width, d.height)
}

fun Window.center() {
    val w = this.width
    val h = this.height
    val (screenw, screenh) = screenSize()
    val x = (screenw - w) / 2
    val y = (screenh - h) / 2
    this.setLocation(x, y)
}