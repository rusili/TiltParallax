package com.rusili.tiltparallax.parallax

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import com.rusili.tiltparallax.R
import kotlinx.android.synthetic.main.view_gxp_parallax_layer.view.gxpParallaxBackground
import kotlinx.android.synthetic.main.view_gxp_parallax_layer.view.gxpParallaxForeground
import kotlinx.android.synthetic.main.view_gxp_parallax_layer.view.gxpParallaxMiddleground

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
        inflate(context, R.layout.view_gxp_parallax_layer, this)

        parallaxViews = listOf<ParallaxImageView>(
            gxpParallaxBackground,
            gxpParallaxMiddleground,
            gxpParallaxForeground)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ParallaxThreeLayerView,
            defStyleAttr, 0
        ).apply {
            try {
                setupViews()
            } finally {
                recycle()
            }
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
            setParallaxIntensity(getFloat(R.styleable.ParallaxThreeLayerView_intensity_background, 1f))
        }
        parallaxViews[1].apply {
            setImageResource(getResourceId(R.styleable.ParallaxThreeLayerView_image_middleground, 0))
            setParallaxIntensity(getFloat(R.styleable.ParallaxThreeLayerView_intensity_middleground, 1f))
        }
        parallaxViews[2].apply {
            setImageResource(getResourceId(R.styleable.ParallaxThreeLayerView_image_foreground, 0))
            setParallaxIntensity(getFloat(R.styleable.ParallaxThreeLayerView_intensity_foreground, 1f))
        }
    }
}
