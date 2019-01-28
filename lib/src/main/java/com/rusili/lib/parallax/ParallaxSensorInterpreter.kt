package com.rusili.lib.parallax

import android.view.Surface
import com.rusili.lib.common.isNotZero

/**
 * Helper class that converts a phone's accelerometer events to x and y axis image translations for [ParallaxImageView]
 *
 * Taken from this stackoverflow answer:
 * https://stackoverflow.com/a/42628846
 */
private const val DEFAULT_HORIZONTAL_TILT_SENSITIVITY = 1.5f
private const val DEFAULT_VERTICAL_TILT_SENSITIVITY = 0.75f
private const val DEFAULT_FORWARD_TILT_OFFSET = 0.3f

class SensorInterpreter {
    private val vectors = Float3()

    var horizontalTiltSensitivity = DEFAULT_HORIZONTAL_TILT_SENSITIVITY
    var verticalTiltSensitivity = DEFAULT_VERTICAL_TILT_SENSITIVITY

    // How vertically centered the image is while the phone is "naturally" tilted forwards.
    var forwardTiltOffset = DEFAULT_FORWARD_TILT_OFFSET

    fun interpretSensorEvent(
        float3: Float3,
        rotation: Int
    ): Float3? =
        vectors.takeIf { float3.noZeroValues() }
            ?.apply {
                setDefaultOrientationValues(rotation)
                setRollPitchPercentages()
                addForwardTilt()
                adjustTiltSensitivity()
                lockToViewBounds()
            }

    /**
     * Adjusts values depending on phone orientation.
     */
    private fun Float3.setDefaultOrientationValues(rotation: Int) =
        when (rotation) {
            Surface.ROTATION_90 -> apply { applyRotation(x, z, -y) }
            Surface.ROTATION_180 -> apply { applyRotation(x, y, z) }
            Surface.ROTATION_270 -> apply { applyRotation(x, -z, y) }
            else -> apply { applyRotation(x, -y, -z) }
        }

    private fun Float3.setRollPitchPercentages() =
        apply {
            y /= 90f
            z /= 90f
        }

    private fun Float3.addForwardTilt() =
        apply {
            y -= forwardTiltOffset
            if (y < -1) {
                y += 2f
            }
        }

    private fun Float3.adjustTiltSensitivity() =
        apply {
            y *= verticalTiltSensitivity
            z *= horizontalTiltSensitivity
        }

    /**
     * Don't allow the image to pan past the bounds of the image.
     */
    private fun Float3.lockToViewBounds() =
        when {
            y > 1 -> apply { y = 1f }
            y < -1 -> apply { y = -1f }
            z > 1 -> apply { z = 1f }
            z < -1 -> apply { z = -1f }
            else -> this
        }
}

data class Float3(
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var z: Float = 0.0f
) {
    constructor(array: FloatArray) : this(array[0], array[1], array[2])

    internal fun applyRotation(
        newX: Float,
        newY: Float,
        newZ: Float
    ) {
        x = newX
        y = newY
        z = newZ
    }

    internal fun noZeroValues() =
        x.isNotZero() && y.isNotZero() && z.isNotZero()
}
