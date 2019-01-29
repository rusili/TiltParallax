package com.rusili.lib.parallax

import com.rusili.lib.common.isNotZero

data class Event3(
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

    internal fun isValidEvent() =
        x.isNotZero() && y.isNotZero() && z.isNotZero()
}
