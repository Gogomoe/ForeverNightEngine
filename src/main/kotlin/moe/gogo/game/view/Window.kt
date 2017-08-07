package moe.gogo.game.view

import javax.swing.JFrame

/**
 * 游戏窗口包含[游戏面板][panel]，可以添加在[游戏面板][panel]上加载场景
 *
 * 窗口通常情况下需要在[WindowFactory]设置一些窗口基本属性
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
     * 用于DSL的表达式，应该长这样
     *
     * ```kotlin
     * window {
     *      title = "ForeverNightEngine"
     *      scene(::BaseScene) {
     *          ...
     *      }
     *      isVisible = true
     * }
     * ```
     */
    open operator fun invoke(call: Window.() -> Unit) {
        call()
    }

    /**
     * 用于DSL的加载场景，并对场景初始化，请参考[invoke]的文档示例
     *
     * @param sceneBuilder 构建scene的构造器
     * @param call 对场景初始化的后续操作
     *
     * @see invoke
     */
    open fun scene(sceneBuilder: () -> Scene, call: Scene.() -> Unit = {}) {
        val scene = panel.load(sceneBuilder())
        call(scene)
    }

}