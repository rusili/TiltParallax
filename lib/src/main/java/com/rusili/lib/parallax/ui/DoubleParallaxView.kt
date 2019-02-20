package com.rusili.lib.parallax.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.rusili.lib.R
import com.rusili.lib.parallax.domain.DEFAULT_FORWARD_TILT_OFFSET
import com.rusili.lib.parallax.domain.DEFAULT_TILT_SENSITIVITY

/**
 * Stacks two [ParallaxView]s on top of each other to simulate a foreground and background parallax effect.
 */
class DoubleParallaxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val parallaxViews: List<ParallaxView>

    init {
        inflate(context, R.layout.view_triple_parallax, this)

        parallaxViews = listOf(
            findViewById(R.id.doubleParallaxBackground),
            findViewById(R.id.doubleParallaxForeground)
        )

        context.withStyledAttributes(
            attrs,
            R.styleable.DoubleParallaxView,
            defStyleAttr, 0
        ) {
            setupViews()
        }
    }

    fun getBackgroundLayer() =
        parallaxViews[0]

    fun getForegroundgroundLayer() =
        parallaxViews[1]

    fun registerSensors() {
        parallaxViews.forEach(ParallaxView::registerSensorManager)
    }

    fun unregisterSensors() {
        parallaxViews.forEach(ParallaxView::unregisterSensorManager)
    }

    private fun TypedArray.setupViews() {
        parallaxViews[0].apply {
            setImageResource(getResourceId(R.styleable.DoubleParallaxView_image_background, 0))
            setParallaxIntensity(getFloat(R.styleable.DoubleParallaxView_parallax_intensity_background, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.DoubleParallaxView_tilt_sensitivity_background, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.DoubleParallaxView_forward_tilt_offset_background, DEFAULT_FORWARD_TILT_OFFSET))
            setMaximumChange(getFloat(R.styleable.DoubleParallaxView_max_translation_background, DEFAULT_MAXIMUM_TRANSLATION))
            setScaleIntensityPerAxis(getBoolean(R.styleable.DoubleParallaxView_scaled_intensity_background, true))
        }
        parallaxViews[1].apply {
            setImageResource(getResourceId(R.styleable.DoubleParallaxView_image_foreground, 0))
            setParallaxIntensity(getFloat(R.styleable.DoubleParallaxView_parallax_intensity_foreground, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.DoubleParallaxView_tilt_sensitivity_foreground, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.DoubleParallaxView_forward_tilt_offset_foreground, DEFAULT_FORWARD_TILT_OFFSET))
            setMaximumChange(getFloat(R.styleable.DoubleParallaxView_max_translation_foreground, DEFAULT_MAXIMUM_TRANSLATION))
            setScaleIntensityPerAxis(getBoolean(R.styleable.DoubleParallaxView_scaled_intensity_foreground, true))
        }
    }
}
