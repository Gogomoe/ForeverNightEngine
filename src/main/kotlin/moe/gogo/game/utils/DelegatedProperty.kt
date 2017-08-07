package moe.gogo.game.utils

/**
 * 延迟初始化的类，通过[init]方法进行延迟初始化，未初始化时使用[orNull]
 *
 * 主要目的是解决初始化懒加载对象时，懒加载的逻辑混在一起，逻辑不清晰，如下面这个例子
 *
 * ```kotlin
 * var s: Subscriber?
 * fun emit() {
 *      e?.emit(event) // 若没有添加 listener 则不触发
 * }
 * fun addListener(listener) {
 *      if (e == null) {
 *          e = Subscriber()
 *      }
 *      e!!.subscribe(listener)
 * }
 * ```
 *
 * 使用[LazyProperty]代替，逻辑会更加清晰
 *
 * ```kotlin
 * val s: LazyProperty<Subscriber> = LazyProperty { Subscriber() }
 * fun emit() {
 *      e.orNull().emit(event)
 * }
 * fun addListener(listener) {
 *      s.init()
 *      e().subscribe(listener)
 * }
 * ```
 *
 * 当然使用这个类也有多此一举的可能，例如上面这个例子，
 * 不使用懒加载，或直接按第一种写法，也可以工作得很好
 */
class LazyProperty<R>(private val initializer: () -> R) {

    private var initialized = false
    private var property: R? = null

    fun init() {
        if (notInitialized()) {
            property = initializer()
            initialized = true
        }
    }

    private fun notInitialized(): Boolean = !initialized && property == null

    fun get(): R {
        if (notInitialized()) {
            throw IllegalStateException("LazyProperty not initialized")
        }
        return property!!
    }

    operator fun invoke() = get()

    fun orNull(): R? = property

    fun set(value: R) = kotlin.run { property = value }

}

/**
 * 主要是表达读取某个值（而不是创造一个值）语义更加明确
 */
typealias ReadOnlyDelegate<T> = () -> T

/**
 * 表达一个可以修改的值
 */
interface ReadWriteDelegate<T> {
    fun get(): T
    fun set(value: T)
    operator fun invoke() = get()
    operator fun invoke(value: T) = set(value)
}

fun <T> ReadWriteDelegate(getter: () -> T, writer: (T) -> Unit) = object : ReadWriteDelegate<T> {

    override fun get(): T = getter()

    override fun set(value: T) = writer(value)

}