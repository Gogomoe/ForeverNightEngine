package moe.gogo.game.view

import moe.gogo.game.shape.Rect
import moe.gogo.game.utils.Point
import moe.gogo.game.utils.antialias
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

/**
 * 游戏面板，负责在[窗体][Window]上显示游戏内容
 *
 * 可以通过[load]加载一个场景显示在界面上，并通过[camera]获取、设置显示的范围
 */
abstract class Panel : JPanel() {

    /**
     * 当前正在显示的场景
     */
    abstract var scene: Scene?

    /**
     * 当前显示的场景的范围
     */
    abstract val camera: Camera

    /**
     * 加载一个场景，使之显示在界面上
     * @param scene 需要加载的场景
     * @return 被加载的场景
     */
    abstract fun load(scene: Scene): Scene

    /**
     * 取消场景的显示
     */
    abstract fun unload()

}

class PanelImpl : Panel() {

    override var scene: Scene? = null

    override val camera: Camera = object : Camera() {
        override var screenRange: Rect = super.screenRange
            get() = Rect(width, height, centerPoint())

        override var position: Point = super.position
        //TODO 目前没有支持缩放，因此游戏范围和屏幕范围大小相同
        override var gameRange: Rect = super.gameRange
            get() = screenRange.shiftTo(position)

        override fun shiftTo(point: Point) {
            this.position = point
        }

        private fun centerPoint(): Point = Point(width / 2, height / 2)

    }

    override fun load(scene: Scene): Scene {
        this.scene = scene
        return scene
    }

    override fun unload() {
        this.scene = null
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (g is Graphics2D) {
            g.antialias()
            /**
            TODO 这行需要改，渲染不应该放在这，
            应该放在System里用一组线程定时渲染
            然而现在System还没做,先将就一下
             */
            scene?.render(camera)
            scene?.image?.let { g.drawImage(it, 0, 0, null) }
        }
    }
}