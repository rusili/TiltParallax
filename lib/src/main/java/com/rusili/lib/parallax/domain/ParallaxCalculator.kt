package com.rusili.lib.parallax.domain

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
        intensity: Float,
        drawableHeight: Float,
        drawableWidth: Float,
        viewHeight: Float,
        viewWidth: Float
    ): Float =
        when (drawableWidth * viewHeight > viewWidth * drawableHeight) {
            true -> viewHeight / drawableHeight * intensity
            false -> viewWidth / drawableWidth * intensity
        }

    internal fun axisOffset(
        scale: Float,
        drawableAxis: Float,
        viewAxis: Float,
        axisTranslation: Float
    ): Float =
        ((viewAxis - drawableAxis * scale) * DEFAULT_OFFSET_MULTIPLIER) + axisTranslation
}
