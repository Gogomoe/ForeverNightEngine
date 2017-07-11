package moe.gogo.game.component

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.StringSpec
import moe.gogo.test.TestStatus

class ComponentContainerTest : StringSpec() {

    init {
        "add component"{
            val container = createContainer()
            val a = createComponentA()
            val b = createComponentB()
            container.set(a)
            container.set(b)

            container[ComponentA::class] shouldBe a
            container.get<ComponentB>() shouldBe b

            (a::class in container) shouldBe true
            container.has<ComponentB>() shouldBe true
        }
        "get or null component"{
            val container = createContainer()
            val a = createComponentA()
            container[ComponentA::class] = a

            container.getOrNull<ComponentA>() shouldBe a
            shouldThrow<KotlinNullPointerException> {
                container[ComponentB::class]
            }
            container.getOrNull(ComponentB::class) shouldBe null
        }
        "replace component"{
            val state = TestStatus()
            val container = createContainer()
            container[ComponentA::class] = object : ComponentA() {
                override fun init(c: ComponentContainer) {
                    super.init(container)
                    state.next()
                }

                override fun destroy() {
                    super.destroy()
                    state.next()
                }
            }
            container[ComponentA::class] = object : ComponentA() {
                override fun init(c: ComponentContainer) {
                    super.init(container)
                    state.next()
                }

                override fun replace(other: Component) {
                    super.replace(other)
                    state.next()
                }
            }
            state.value shouldBe 4
        }
        "add repeatedly"{
            val container = createContainer()
            val state = TestStatus()
            val a = object : ComponentA() {
                override fun init(c: ComponentContainer) {
                    super.init(container)
                    state.next()
                }
            }
            container[ComponentA::class] = a
            container[ComponentA::class] = a
            state.value shouldBe 1
        }
        "add on other"{
            val container = createContainer()
            val other = createContainer()
            val a = createComponentA()

            other[ComponentA::class] = a
            shouldThrow<IllegalStateException> {
                container[ComponentA::class] = a
                Unit
            }
        }
        "depend on other component"{
            shouldThrow<IllegalStateException> {
                val container = createContainer()
                container[ComponentA::class] = createComponentA()
                container[Component::class] = object : Component() {
                    override fun init(container: ComponentContainer) {
                        dependOn<ComponentA>()
                        super.init(container)
                    }
                }
                Unit
            }
            shouldThrow<IllegalStateException> {
                val container = createContainer()
                container[ComponentDependOnA::class] = createComponentDependOnA()
                Unit
            }
            val container = createContainer()
            container[ComponentA::class] = createComponentA()
            container[ComponentDependOnA::class] = createComponentDependOnA()
            container[ComponentDependOnA::class] shouldNotBe null
        }
    }

    fun createContainer() = object : ComponentContainer() {}

    open class ComponentA : Component()
    open class ComponentB : Component()
    open class ComponentDependOnA : Component() {
        override fun init(container: ComponentContainer) {
            super.init(container)
            dependOn<ComponentA>()
        }
    }

    fun createComponentA() = ComponentA()
    fun createComponentB() = ComponentB()
    fun createComponentDependOnA() = ComponentDependOnA()

}