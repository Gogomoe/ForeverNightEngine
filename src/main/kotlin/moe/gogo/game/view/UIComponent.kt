package moe.gogo.game.view

import moe.gogo.game.component.ComponentContainer
import moe.gogo.game.component.EmptyRenderComponent
import moe.gogo.game.component.MouseEventComponent
import moe.gogo.game.component.PositionComponent
import moe.gogo.game.component.RelativePositionComponent
import moe.gogo.game.component.RenderComponent
import moe.gogo.game.component.ShapeComponent
import moe.gogo.game.component.UITreeComponent
import moe.gogo.game.utils.Point
import java.awt.Graphics2D

/**
 * UI组件通常是与游戏逻辑无关的交互组件，
 * 例如游戏底层的背景、顶层的按钮、边框的装饰等
 *
 * UI组件可以通过[UITreeComponent]添加到另一个UI组件上
 *
 * UI组件实现[形状组件][ShapeComponent]和[鼠标事件组件][MouseEventComponent]后可以相应鼠标事件
 */
abstract class UIComponent : ComponentContainer() {

    init {
        set(UITreeComponent::class, UITreeComponent())
        set(PositionComponent::class, PositionComponent())
        set(RelativePositionComponent::class, RelativePositionComponent(this))
        set(RenderComponent::class, EmptyRenderComponent())
    }

    var position: Point
        get() = positionComponent.position
        set(value) {
            positionComponent.position = value
        }

    var x: Float
        get() = position.x
        set(value) {
            position = position.setX(value)
        }

    var y: Float
        get() = position.y
        set(value) {
            position = position.setY(value)
        }

    var relativePosition: Point
        get() = relativePositionComponent.position
        set(value) {
            relativePositionComponent.position = value
        }

    val parent: UIComponent?
        get() = treeComponent.parent

    val children: Set<UIComponent>
        get() = treeComponent.children

    fun render(graphics2D: Graphics2D) = renderComponent.render(graphics2D)

    val positionComponent: PositionComponent
        get() = this[PositionComponent::class]

    val relativePositionComponent: RelativePositionComponent
        get() = this[RelativePositionComponent::class]

    val treeComponent: UITreeComponent
        get() = this[UITreeComponent::class]

    val renderComponent: RenderComponent
        get() = this[RenderComponent::class]
}