package moe.gogo.game.view

import moe.gogo.game.input.MouseEventHandler
import java.awt.image.BufferedImage

/**
 * Scene 描述一个场景
 *
 * 场景中通常包括很多[层][Layer]，底层的背景、中层的游戏对象、顶层的提示信息等，
 * Scene主要管理这些Layer，并适时渲染各Layer，将鼠标事件分发至Layer
 *
 * 通过场景可以获取所有添加的层，默认按 底层的Layer位于List前面 的顺序排序
 *
 * 场景需要被加载到游戏窗口上，参见[Panel.load]
 *
 * @see Layer
 * @see Panel
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
     * @param builder 根据当前场景创建层
     * @param index 当前场景添加的位置
     * @return 构建出的Layer
     */
    abstract fun <T : Layer> createLayer(builder: (Scene) -> T, index: Int = size): T

    /**
     * 渲染此层，渲染后会更新[image]属性
     * @param camera 描述视图信息的camera
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

    /**
     * 用于DSL的添加Layer的方式，比如像这么用
     *
     * ```kotlin
     * scene {
     *      // EntityLayer位于底层
     *      layer(::EntityLayer) {
     *          ...
     *      }
     *      // UILayer位于顶层
     *      layer(::UILayer) {
     *          ...
     *      }
     * }
     * ```
     */
    open fun <T : Layer> layer(layerBuilder: (Scene) -> T, call: T.() -> Unit = {}) {
        val layer = createLayer(layerBuilder)
        call(layer)
    }

}