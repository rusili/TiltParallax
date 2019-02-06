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
 * Stacks three [ParallaxImageView]s on top of each other to simulate a 3d parallax effect.
 */
class ParallaxThreeLayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val parallaxViews: List<ParallaxImageView>

    init {
        inflate(context, R.layout.view_parallax_three_layer, this)

        parallaxViews = listOf(
            findViewById(R.id.parallaxBackground),
            findViewById(R.id.parallaxMiddleground),
            findViewById(R.id.parallaxForeground)
        )

        context.withStyledAttributes(
            attrs,
            R.styleable.ParallaxThreeLayerView,
            defStyleAttr, 0
        ) {
            setupViews()
        }
    }

    fun registerSensors() {
        parallaxViews.forEach(ParallaxImageView::registerSensorManager)
    }

    fun unregisterSensors() {
        parallaxViews.forEach(ParallaxImageView::unregisterSensorManager)
    }

    private fun TypedArray.setupViews() {
        parallaxViews[0].apply {
            setImageResource(getResourceId(R.styleable.ParallaxThreeLayerView_image_background, 0))
            setParallaxIntensity(getFloat(R.styleable.ParallaxThreeLayerView_intensity_background, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.ParallaxThreeLayerView_tilt_sensitivity_background, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.ParallaxThreeLayerView_forward_tilt_offset_background, DEFAULT_FORWARD_TILT_OFFSET))
            setScaleIntensityPerAxis(getBoolean(R.styleable.ParallaxThreeLayerView_scaled_intensity_background, true))
        }
        parallaxViews[1].apply {
            setImageResource(getResourceId(R.styleable.ParallaxThreeLayerView_image_middleground, 0))
            setParallaxIntensity(getFloat(R.styleable.ParallaxThreeLayerView_intensity_middleground, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.ParallaxThreeLayerView_tilt_sensitivity_middleground, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.ParallaxThreeLayerView_forward_tilt_offset_middleground, DEFAULT_FORWARD_TILT_OFFSET))
            setScaleIntensityPerAxis(getBoolean(R.styleable.ParallaxThreeLayerView_scaled_intensity_middleground, true))
        }
        parallaxViews[2].apply {
            setImageResource(getResourceId(R.styleable.ParallaxThreeLayerView_image_foreground, 0))
            setParallaxIntensity(getFloat(R.styleable.ParallaxThreeLayerView_intensity_foreground, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.ParallaxThreeLayerView_tilt_sensitivity_foreground, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.ParallaxThreeLayerView_forward_tilt_offset_foreground, DEFAULT_FORWARD_TILT_OFFSET))
            setScaleIntensityPerAxis(getBoolean(R.styleable.ParallaxThreeLayerView_scaled_intensity_foreground, true))
        }
    }
}
