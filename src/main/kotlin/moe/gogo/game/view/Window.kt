package moe.gogo.game.view

import javax.swing.JFrame

/**
 * 游戏窗口包含[游戏面板][panel]，通常情况下需要在[WindowFactory]设置一些窗口基本属性
 */
abstract class Window : JFrame() {

    /**
     * 游戏面板
     */
    abstract val panel: Panel

    /**
     * 游戏视图，默认即是面板的视图
     */
    open val camera
        get() = panel.camera

    /**
     * 重设窗体大小，会同时改变面板大小
     */
    open fun size(width: Int, height: Int) {
        setSize(width, height)
        panel.setSize(width, height)
    }

    /**
     * 用于DSL的表达式
     */
    open operator fun invoke(call: Window.() -> Unit) {
        call()
    }

    /**
     * 用于DSL的加载场景，并对场景初始化
     */
    open fun scene(sceneBuilder: () -> Scene, call: Scene.() -> Unit = {}) {
        val scene = panel.load(sceneBuilder())
        call(scene)
    }

}