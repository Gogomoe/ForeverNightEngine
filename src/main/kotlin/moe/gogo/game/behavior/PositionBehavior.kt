package moe.gogo.game.behavior

class PositionBehavior {

    var point: Point = Point(0f, 0f)

    var x: Float
        get() = point.x
        set(value) {
            point = Point(value, y)
        }

    var y: Float
        get() = point.y
        set(value) {
            point = Point(x, value)
        }

    operator fun plus(vector: Vector) {
        point += vector
    }

}