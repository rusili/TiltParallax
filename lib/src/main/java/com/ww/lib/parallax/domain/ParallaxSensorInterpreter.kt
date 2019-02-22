/*
* Copyright (C) 2019. WW International, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.ww.lib.parallax.domain

import android.view.Surface

/**
 * Helper class that converts a phone's accelerometer events to x and y axis translations for [ParallaxImageView]
 *
 * Taken from this stackoverflow answer:
 * https://stackoverflow.com/a/42628846
 */
internal const val DEFAULT_TILT_SENSITIVITY = 2f
internal const val DEFAULT_FORWARD_TILT_OFFSET = 0.3f

internal class SensorInterpreter {
    internal var tiltSensitivity = DEFAULT_TILT_SENSITIVITY

    // How vertically centered the image is while the phone is "naturally" tilted forwards.
    internal var forwardTiltOffset = DEFAULT_FORWARD_TILT_OFFSET

    internal fun interpretSensorEvent(
        event: Event3,
        rotation: Int
    ): Event3? =
        Event3().takeIf(Event3::isValid)
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
            y *= tiltSensitivity
            z *= tiltSensitivity
        }

    /**
     * Don't allow the image to scroll past the bounds of the image.
     */
    private fun Event3.lockToViewBounds() =
        apply {
            when {
                y >= 1 -> y = 1f
                y <= -1 -> y = -1f
                z >= 1 -> z = 1f
                z <= -1 -> z = -1f
            }
        }
}
