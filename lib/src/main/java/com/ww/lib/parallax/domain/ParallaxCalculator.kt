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

/**
 * Helper class that calculates [ParallaxImageView] translations
 *
 * Taken from this stackoverflow answer:
 * https://stackoverflow.com/a/42628846
 */
private const val DEFAULT_OFFSET_MULTIPLIER = 0.5f

internal class ParallaxCalculator {

    internal fun getScale(
        mainAxisOffset: Float,
        otherAxisOffset: Float,
        scaleIntensityPerAxis: Boolean = false
    ): Float =
        if (scaleIntensityPerAxis) mainAxisOffset else Math.max(mainAxisOffset, otherAxisOffset)

    // Make sure below maximum maxTranslationChange limit
    internal fun translate(
        maxTranslationChange: Float,
        translation: Float,
        axis: Float,
        scale: Float
    ): Float {
        if (maxTranslationChange > 0) {
            if (axis - translation / scale > maxTranslationChange) {
                return (translation / scale + maxTranslationChange) * scale
            } else if (axis - translation / scale < -maxTranslationChange) {
                return (translation / scale - maxTranslationChange) * scale
            }
        }
        return axis * scale
    }

    internal fun overallScale(
        drawableHeight: Float,
        drawableWidth: Float,
        viewHeight: Float,
        viewWidth: Float
    ): Float =
        when (drawableWidth * viewHeight > viewWidth * drawableHeight) {
            true -> viewHeight / drawableHeight
            false -> viewWidth / drawableWidth
        }

    internal fun axisOffset(
        intensity: Float,
        scale: Float,
        drawableAxis: Float,
        viewAxis: Float
    ): Float =
        (viewAxis - drawableAxis * scale * intensity) * DEFAULT_OFFSET_MULTIPLIER
}
