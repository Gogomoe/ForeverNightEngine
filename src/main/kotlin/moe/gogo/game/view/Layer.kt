package moe.gogo.game.view

import moe.gogo.game.input.MouseEvent
import moe.gogo.game.input.MouseEventHandler
import java.awt.image.BufferedImage

/**
 * [场景][Scene]是由[层][Layer]组成的，各层之间有先后关系，上层会覆盖下层
 *
 * 场景通过[mouseEventHandler]处理鼠标事件
 */
abstract class Layer(val scene: Scene) {

    /**
     * 渲染此层，渲染后会更新[image]属性
     */
    abstract fun render(view: View)

    /**
     * 上次渲染后的图像
     */
    abstract val image: BufferedImage

    /**
     * 用于处理鼠标事件的处理器
     */
    abstract val mouseEventHandler: MouseEventHandler

}