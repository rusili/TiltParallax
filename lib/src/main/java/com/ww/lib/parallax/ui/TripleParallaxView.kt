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
package com.ww.lib.parallax.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.ww.lib.R
import com.ww.lib.parallax.domain.DEFAULT_FORWARD_TILT_OFFSET
import com.ww.lib.parallax.domain.DEFAULT_TILT_SENSITIVITY

/**
 * Stacks three [ParallaxView]s on top of each other to simulate a foreground, middleground, and background parallax effect.
 */
class TripleParallaxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val parallaxViews: List<ParallaxView>

    init {
        inflate(context, R.layout.view_triple_parallax, this)

        parallaxViews = listOf(
            findViewById(R.id.tripleParallaxBackground),
            findViewById(R.id.tripleParallaxMiddleground),
            findViewById(R.id.tripleParallaxForeground)
        )

        context.withStyledAttributes(
            attrs,
            R.styleable.TripleParallaxView,
            defStyleAttr, 0
        ) {
            setupViews()
        }
    }

    fun getBackgroundLayer() =
        parallaxViews[0]

    fun getMiddlegroundLayer() =
        parallaxViews[1]

    fun getForegroundgroundLayer() =
        parallaxViews[2]

    fun registerSensors() {
        parallaxViews.forEach(ParallaxView::registerSensor)
    }

    fun unregisterSensors() {
        parallaxViews.forEach(ParallaxView::unregisterSensor)
    }

    private fun TypedArray.setupViews() {
        parallaxViews[0].apply {
            setImageResource(getResourceId(R.styleable.TripleParallaxView_image_background, 0))
            setParallaxIntensity(getFloat(R.styleable.TripleParallaxView_parallax_intensity_background, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.TripleParallaxView_tilt_sensitivity_background, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.TripleParallaxView_forward_tilt_offset_background, DEFAULT_FORWARD_TILT_OFFSET))
            setMaximumChange(getFloat(R.styleable.TripleParallaxView_max_translation_background, DEFAULT_MAXIMUM_TRANSLATION))
            setScaleIntensityPerAxis(getBoolean(R.styleable.TripleParallaxView_scaled_intensity_background, true))
        }
        parallaxViews[1].apply {
            setImageResource(getResourceId(R.styleable.TripleParallaxView_image_middleground, 0))
            setParallaxIntensity(getFloat(R.styleable.TripleParallaxView_parallax_intensity_middleground, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.TripleParallaxView_tilt_sensitivity_middleground, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.TripleParallaxView_forward_tilt_offset_middleground, DEFAULT_FORWARD_TILT_OFFSET))
            setMaximumChange(getFloat(R.styleable.TripleParallaxView_max_translation_middleground, DEFAULT_MAXIMUM_TRANSLATION))
            setScaleIntensityPerAxis(getBoolean(R.styleable.TripleParallaxView_scaled_intensity_middleground, true))
        }
        parallaxViews[2].apply {
            setImageResource(getResourceId(R.styleable.TripleParallaxView_image_foreground, 0))
            setParallaxIntensity(getFloat(R.styleable.TripleParallaxView_parallax_intensity_foreground, DEFAULT_INTENSITY_MULTIPLIER))
            setTiltSensitivity(getFloat(R.styleable.TripleParallaxView_tilt_sensitivity_foreground, DEFAULT_TILT_SENSITIVITY))
            setForwardTiltOffset(getFloat(R.styleable.TripleParallaxView_forward_tilt_offset_foreground, DEFAULT_FORWARD_TILT_OFFSET))
            setMaximumChange(getFloat(R.styleable.TripleParallaxView_max_translation_foreground, DEFAULT_MAXIMUM_TRANSLATION))
            setScaleIntensityPerAxis(getBoolean(R.styleable.TripleParallaxView_scaled_intensity_foreground, true))
        }
    }
}
