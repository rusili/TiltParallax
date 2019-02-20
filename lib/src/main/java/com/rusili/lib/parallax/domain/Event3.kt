package com.rusili.lib.parallax.domain

internal data class Event3(
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var z: Float = 0.0f
) {
    constructor(array: FloatArray) : this(array[0], array[1], array[2])

    internal fun rotate(
        newX: Float,
        newY: Float,
        newZ: Float
    ) {
        x = newX
        y = newY
        z = newZ
    }

    internal fun isValid() =
        x != 0f && y != 0f && z != 0f
}
