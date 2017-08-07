package moe.gogo.game.utils


/**
 * 描述值的变化，一个值从旧值变为新值
 * @param old 旧值
 * @param new 新值
 */
data class MutativeValue<out T>(val old: T, val new: T) {

    override fun toString(): String = "MutativeValue($old -> $new)"

}