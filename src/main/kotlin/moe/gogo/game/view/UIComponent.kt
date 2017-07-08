package moe.gogo.game.view

import moe.gogo.game.component.AbsolutePositionComponent
import moe.gogo.game.component.AbsolutePositionComponentImpl
import moe.gogo.game.component.ComponentContainer
import moe.gogo.game.component.EmptyRenderComponent
import moe.gogo.game.component.PositionComponent
import moe.gogo.game.component.RenderComponent
import moe.gogo.game.component.UITreeComponent
import moe.gogo.game.utils.Point
import java.awt.Graphics2D

abstract class UIComponent : ComponentContainer() {

    init {
        set(UITreeComponent::class, UITreeComponent())
        set(PositionComponent::class, PositionComponent())
        set(AbsolutePositionComponent::class, AbsolutePositionComponentImpl(this))
        set(RenderComponent::class, EmptyRenderComponent())
    }

    var position: Point
        get() = positionComponent.position
        set(value) = run { positionComponent.position = value }

    var absolutePosition: Point
        get() = absolutePositionComponent.position
        set(value) = run { absolutePositionComponent.position = value }

    val parent: UIComponent?
        get() = treeComponent.parent

    val children: Set<UIComponent>
        get() = treeComponent.children

    fun render(graphics2D: Graphics2D) = renderComponent.render(graphics2D)

    val positionComponent: PositionComponent
        get() = this[PositionComponent::class]

    val absolutePositionComponent: AbsolutePositionComponent
        get() = this[AbsolutePositionComponent::class]

    val treeComponent: UITreeComponent
        get() = this[UITreeComponent::class]

    val renderComponent: RenderComponent
        get() = this[RenderComponent::class]


}