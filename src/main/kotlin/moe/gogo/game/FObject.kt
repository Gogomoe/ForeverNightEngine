package moe.gogo.game

import moe.gogo.game.behavior.CollideBehavior
import moe.gogo.game.behavior.CollideBox
import moe.gogo.game.behavior.Container
import moe.gogo.game.behavior.ObjectContainer
import moe.gogo.game.behavior.Point
import moe.gogo.game.behavior.PositionBehavior
import moe.gogo.game.behavior.RenderdBehavior
import moe.gogo.game.behavior.Renderer
import moe.gogo.game.utils.lazyVar
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * FObject 是可以显示在场景中的基本对象
 *
 * 游戏中的单位、道具，游戏界面上提示的文字、按钮均是FObject
 */
abstract class FObject : Container, CollideBox, Renderer {

    /**
     * Container
     */
    var parent: Container? = null

    var container: Container
            by lazyVar {
                ObjectContainer(this)
            }

    override fun add(obj: FObject) = container.add(obj)

    override fun remove(obj: FObject) = container.remove(obj)

    override fun children(): Collection<FObject> = container.children()

    /**
     * position 表示的是该对象相对于 [parent] 对象的相对位置
     * @see absolutePosition
     */
    val position: PositionBehavior = PositionBehavior()

    /**
     * absolutePosition 表示的是在场景中的绝对位置
     * @see position
     */
    val absolutePosition: PositionBehavior = PositionBehavior()

    /**
     * CollideBox
     */
    abstract var collide: CollideBehavior

    override fun isCollide(other: FObject): Boolean = collide.isCollide(other)

    override fun containsPoint(point: Point): Boolean = collide.containsPoint(point)

    /**
     * Renderer
     */
    abstract var renderer: RenderdBehavior

    override fun renderImage(): BufferedImage = renderer.renderImage()

    override fun render(graphics: Graphics2D) = renderer.render(graphics)
}
