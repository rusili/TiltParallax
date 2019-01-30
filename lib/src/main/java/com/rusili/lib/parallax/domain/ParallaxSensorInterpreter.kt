package com.rusili.lib.parallax.domain

import android.view.Surface

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
    private val vectors = Event3()

    internal var horizontalTiltSensitivity = DEFAULT_HORIZONTAL_TILT_SENSITIVITY
    internal var verticalTiltSensitivity = DEFAULT_VERTICAL_TILT_SENSITIVITY

    // How vertically centered the image is while the phone is "naturally" tilted forwards.
    internal var forwardTiltOffset = DEFAULT_FORWARD_TILT_OFFSET

    fun interpretSensorEvent(
        event: Event3,
        rotation: Int
    ): Event3? =
        vectors.takeIf { event.isValidEvent() }
            ?.copy(event.x, event.y, event.z)
            ?.apply {
                applyRotation(rotation)
                setRollPitchPercentages()
                addForwardTilt()
                adjustTiltSensitivity()
                lockToViewBounds()
            }

    /**
     * Adjusts values depending on phone orientation.
     */
    private fun Event3.applyRotation(rotation: Int) =
        apply {
            when (rotation) {
                Surface.ROTATION_90 -> rotate(x, z, -y)
                Surface.ROTATION_180 -> rotate(x, y, z)
                Surface.ROTATION_270 -> rotate(x, -z, y)
                else -> rotate(x, -y, -z)
            }
        }

    private fun Event3.setRollPitchPercentages() =
        apply {
            y /= 90f
            z /= 90f
        }

    private fun Event3.addForwardTilt() =
        apply {
            y -= forwardTiltOffset
            if (y < -1) {
                y += 2f
            }
        }

    private fun Event3.adjustTiltSensitivity() =
        apply {
            y *= verticalTiltSensitivity
            z *= horizontalTiltSensitivity
        }

    /**
     * Don't allow the image to pan past the bounds of the image.
     */
    private fun Event3.lockToViewBounds() =
        apply {
            when {
                y > 1 -> y = 1f
                y < -1 -> y = -1f
                z > 1 -> z = 1f
                z < -1 -> z = -1f
            }
        }
}
