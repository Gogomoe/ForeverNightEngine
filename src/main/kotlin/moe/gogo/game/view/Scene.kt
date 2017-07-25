package moe.gogo.game.view

import moe.gogo.game.input.MouseEventHandler
import java.awt.image.BufferedImage

/**
 * Scene 描述一个场景
 *
 * 场景中通常包括很多[层][Layer]，底层的背景、中层的游戏对象、顶层的提示信息等，
 * Scene主要管理这些Layer，并适时渲染各Layer，将鼠标事件分发至Layer
 *
 */
abstract class Scene {

    /**
     * 获得排序后的所有layer，底层的位于List前面
     */
    abstract val layers: List<Layer>

    /**
     * 当前的层数
     */
    open val size: Int
        get() = layers.size

    /**
     * 根据指定的[构建器][builder]及[位置][index]，创建新的层
     */
    abstract fun createLayer(builder: (Scene) -> Layer, index: Int = size): Layer

    /**
     * 以默认层数创建新层
     */
    abstract fun createLayer(): Layer

    /**
     * 渲染此层，渲染后会更新[image]属性
     */
    abstract fun render(camera: Camera)

    /**
     * 上次已经渲染的图片
     */
    abstract val image: BufferedImage

    /**
     * 用于处理鼠标事件的处理器
     */
    abstract val mouseEventHandler: MouseEventHandler

}