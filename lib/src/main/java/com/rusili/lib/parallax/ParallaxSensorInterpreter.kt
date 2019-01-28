package com.rusili.lib.parallax

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

    internal var horizontalTiltSensitivity = DEFAULT_HORIZONTAL_TILT_SENSITIVITY
    internal var verticalTiltSensitivity = DEFAULT_VERTICAL_TILT_SENSITIVITY

    // How vertically centered the image is while the phone is "naturally" tilted forwards.
    internal var forwardTiltOffset = DEFAULT_FORWARD_TILT_OFFSET

    fun interpretSensorEvent(float3: Float3): Float3? =
        vectors.takeIf { validEvent(float3) }
            ?.apply {
                setDefaultOrientationValues(float3)
                setRollPitchPercentages()
                addForwardTilt()
                adjustTiltSensitivity()
                lockToViewBounds()
            }

    /**
     * Adjusts values depending on phone orientation. App is locked to portrait, so no need to change for other orientations.
     */
    private fun Float3.setDefaultOrientationValues(event: Float3) =
        apply {
            this.x = event.x
            this.y = -event.y
            this.z = -event.z
        }

    private fun Float3.setRollPitchPercentages() =
        apply {
            this.y /= 90f
            this.z /= 90f
        }

    private fun Float3.addForwardTilt() =
        apply {
            this.y -= forwardTiltOffset
            if (this.y < -1) {
                this.y += 2f
            }
        }

    private fun Float3.adjustTiltSensitivity() =
        apply {
            this.y *= verticalTiltSensitivity
            this.z *= horizontalTiltSensitivity
        }

    /**
     * Don't allow the image to pan past the bounds of the image.
     */
    private fun Float3.lockToViewBounds() =
        apply {
            when {
                this.y > 1 -> this.y = 1f
                this.y < -1 -> this.y = -1f
                this.z > 1 -> this.z = 1f
                this.z < -1 -> this.z = -1f
            }
        }

    private fun validEvent(event: Float3) =
        event.x.isNotZero() && event.y.isNotZero() && event.z.isNotZero()
}

data class Float3(
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var z: Float = 0.0f
) {
    constructor(array: FloatArray) : this(array[0], array[1], array[2])
}
