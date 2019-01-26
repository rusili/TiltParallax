package com.rusili.tiltparallax.parallax

import com.rusili.tiltparallax.common.isNotZero

/**
 * Helper class that converts a phone's accelerometer events to x and y axis image translations for [ParallaxImageView]
 *
 * Taken from this stackoverflow answer:
 * https://stackoverflow.com/a/42628846
 */
private const val DEFAULT_HORIZONTAL_TILT_SENSITIVITY = 1.5f
private const val DEFAULT_VERTICAL_TILT_SENSITIVITY = 0.75f     // By default, vertical sensitivity is 1/2 of horizontal
private const val DEFAULT_FORWARD_TILT_OFFSET = 0.3f

class SensorInterpreter {
    private val vectors = Float3()

    internal var horizontalTiltSensitivity = DEFAULT_HORIZONTAL_TILT_SENSITIVITY
    internal var verticalTiltSensitivity = DEFAULT_VERTICAL_TILT_SENSITIVITY

    // How vertically centered the image is while the phone is "naturally" tilted forwards.
    internal var forwardTiltOffset = DEFAULT_FORWARD_TILT_OFFSET

    fun interpretSensorEvent(float3: Float3): Float3? =
        vectors.takeIf(Float3::noZeroValues)
            ?.apply {
                setDefaultOrientationValues(float3)
                setRollPitchPercentages()
                addForwardTilt()
                adjustTiltSensitivity()
                lockToViewBounds()
            }

    // TODO: Add other orientations
    /**
     * Adjusts values depending on phone orientation.
     */
    private fun Float3.setDefaultOrientationValues(event: Float3) =
        apply {
            x = event.x
            y = -event.y
            z = -event.z
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
        apply {
            when {
                y > 1 -> y = 1f
                y < -1 -> y = -1f
                z > 1 -> z = 1f
                z < -1 -> z = -1f
            }
        }

    private fun validEvent(event: Float3) =
        event.noZeroValues()
}

data class Float3(
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var z: Float = 0.0f
) {
    constructor(array: FloatArray) : this(array[0], array[1], array[2])

    fun noZeroValues() =
        x.isNotZero() && y.isNotZero() && z.isNotZero()
}
