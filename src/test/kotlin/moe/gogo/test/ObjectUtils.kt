package moe.gogo.test

import moe.gogo.game.FObject
import moe.gogo.game.behavior.CollideBehavior
import moe.gogo.game.behavior.RenderdBehavior

fun createEmptyObject() = object : FObject() {
    override var renderer: RenderdBehavior
        get() = TODO("not implemented")
        set(value) {}
    override var collide: CollideBehavior
        get() = TODO("not implemented")
        set(value) {}
}