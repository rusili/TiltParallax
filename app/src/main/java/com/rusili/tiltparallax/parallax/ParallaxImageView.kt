package com.rusili.tiltparallax.parallax

import android.content.Context
import android.graphics.Matrix
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.use
import com.rusili.tiltparallax.R

/**
 * Adds a parallax effect to an [AppCompatImageView] based on the phone's accelerometer.
 * For example, when the user tilts their phone to the left, the image should pan to the right inside the view.
 * Works with [ParallaxCalculator] and [SensorInterpreter] to calculate the correct translations.
 *
 * Example of effect:
 * http://matthew.wagerfield.com/parallax/
 *
 * Taken from this stackoverflow answer:
 * https://stackoverflow.com/a/42628846
 */

private const val DEFAULT_INTENSITY_MULTIPLIER = 1.0f
private const val DEFAULT_MAXIMUM_TRANSLATION = 0.1f

class ParallaxImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle), SensorEventListener {
    /**
     * If the x and y axis' intensities are scaled to the image's aspect ratio (true) or
     * equal to the smaller of the axis' intensities (false). If true, the image will be able to
     * translate up to it's view bounds, independent of aspect ratio. If not true,
     * the image will limit it's translation equally so that motion in either axis results
     * in proportional translation.
     */
    private var scaleIntensityPerAxis = false

    /**
     * The intensity of the parallax effect, giving the perspective of depth.
     */
    private var intensityMultiplier = DEFAULT_INTENSITY_MULTIPLIER

    /**
     * The maximum percentage of offset translation that the image can move for each
     * sensor input. Set to a negative number to disable.
     */
    private var maxTranslationChange = DEFAULT_MAXIMUM_TRANSLATION

    private val sensorInterpreter = SensorInterpreter()
    private val parallaxCalculator = ParallaxCalculator()

    private var sensorManager: SensorManager? = null
    private val translationMatrix = Matrix()

    private var xTranslation: Float = 0f
    private var yTranslation: Float = 0f
    private var xOffset: Float = 0f
    private var yOffset: Float = 0f

    init {
        scaleType = ImageView.ScaleType.MATRIX

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ParallaxImageView,
            defStyle, 0
        ).use {
            setParallaxIntensity(
                it.getFloat(
                    R.styleable.ParallaxImageView_intensity,
                    intensityMultiplier
                )
            )
            setScaledIntensities(
                it.getBoolean(
                    R.styleable.ParallaxImageView_scaled_intensity,
                    scaleIntensityPerAxis
                )
            )
            setHorizontalTiltSensitivity(
                it.getFloat(
                    R.styleable.ParallaxImageView_horizontal_tilt_sensitivity,
                    sensorInterpreter.horizontalTiltSensitivity
                )
            )
            setVerticalTiltSensitivity(
                it.getFloat(
                    R.styleable.ParallaxImageView_vertical_tilt_sensitivity,
                    sensorInterpreter.verticalTiltSensitivity
                )
            )
            setForwardTiltOffset(
                it.getFloat(
                    R.styleable.ParallaxImageView_forward_tilt_offset,
                    sensorInterpreter.forwardTiltOffset
                )
            )
        }

        // Configure matrix as early as possible by posting to MessageQueue
        post(::configureMatrix)
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        configureMatrix()
    }

    override fun onSensorChanged(event: SensorEvent) {
        val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
            .defaultDisplay
            .rotation

        sensorInterpreter.interpretSensorEvent(Float3(event.values), rotation)
            ?.let { float3 ->
                setTranslate(float3.z, float3.y)
                configureMatrix()
            }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) = Unit

    /**
     * Registers a sensor manager with the parallax ImageView. Should be called in onResume
     * from an Activity or Fragment.
     */
    fun registerSensorManager() {
        sensorManager = (context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager)
            ?.apply {
                registerListener(
                    this@ParallaxImageView,
                    getDefaultSensor(Sensor.TYPE_ORIENTATION),
                    SensorManager.SENSOR_DELAY_FASTEST
                )
            }
    }

    /**
     * Unregisters the ParallaxImageView's SensorManager. Should be called in onPause from
     * an Activity or Fragment to avoid continuing sensor usage.
     */
    fun unregisterSensorManager() {
        sensorManager?.unregisterListener(this)
            .also { it == null }
    }

    /**
     * Sets the intensity of the parallax effect. The stronger the effect, the more distance
     * the image will have to move around.
     *
     * @param parallaxIntensity the new intensity
     * @FloatRange(from = 1.0)
     */
    fun setParallaxIntensity(parallaxIntensity: Float) {
        parallaxIntensity.takeIf { it >= 1 }
            ?.let {
                intensityMultiplier = parallaxIntensity
                configureMatrix()
            } ?: throw IllegalArgumentException("Parallax effect must have a intensity of 1.0 or greater")
    }

    /**
     * Sets the parallax tilt sensitivity for the image view. The stronger the sensitivity,
     * the more a given tilt will adjust the image and the smaller needed tilt to reach the
     * image bounds.
     *
     * @param sensitivity the new tilt sensitivity
     */
    fun setHorizontalTiltSensitivity(sensitivity: Float) {
        sensorInterpreter.horizontalTiltSensitivity = sensitivity
    }

    fun setVerticalTiltSensitivity(sensitivity: Float) {
        sensorInterpreter.verticalTiltSensitivity = sensitivity
    }

    /**
     * Sets the forward tilt offset dimension, allowing for the image to be
     * centered while the phone is "naturally" tilted forwards.
     *
     * @param forwardTiltOffset the new tilt forward adjustment
     * @FloatRange(from = -1.0, to = 1.0)
     */
    fun setForwardTiltOffset(forwardTiltOffset: Float) {
        sensorInterpreter.forwardTiltOffset = forwardTiltOffset
    }

    /**
     * Sets whether translation should be limited to the image's bounds or should be limited
     * to the smaller of the two axis' translation limits.
     *
     * @param scalePerAxis the scalePerAxis flag
     */
    fun setScaledIntensities(scalePerAxis: Boolean) {
        scaleIntensityPerAxis = scalePerAxis
    }

    /**
     * Sets the maximum percentage of the image that image matrix is allowed to translate
     * for each sensor reading.
     *
     * @param maxChange the new maximum jump
     */
    fun setMaximumJump(maxChange: Float) {
        maxTranslationChange = maxChange
    }

    /**
     * Sets the image view's translation coordinates. These values must be between -1 and 1,
     * representing the transaction percentage from the center.
     *
     * @param x the horizontal translation
     * @param y the vertical translation
     */
    private fun setTranslate(
        x: Float,
        y: Float
    ) {
        if (Math.abs(x) > 1 || Math.abs(y) > 1) {
            return
        }

        val xScale = if (scaleIntensityPerAxis) xOffset else Math.max(xOffset, yOffset)
        val yScale = if (scaleIntensityPerAxis) yOffset else Math.max(xOffset, yOffset)

        xTranslation = parallaxCalculator.translate(maxTranslationChange, xTranslation, x, xScale)
        yTranslation = parallaxCalculator.translate(maxTranslationChange, yTranslation, y, yScale)
    }

    /**
     * Configures the ImageView's imageMatrix to allow for movement of the
     * source image.
     */
    private fun configureMatrix() {
        if (drawable == null || width == 0 || height == 0) {
            return
        }

        val drawableHeight = drawable.intrinsicHeight.toFloat()
        val drawableWidth = drawable.intrinsicWidth.toFloat()
        val viewHeight = height.toFloat()
        val viewWidth = width.toFloat()

        val scale = parallaxCalculator.overallScale(drawableHeight, drawableWidth, viewHeight, viewWidth)
        xOffset = parallaxCalculator.axisOffset(intensityMultiplier, scale, drawableWidth, viewWidth)
        yOffset = parallaxCalculator.axisOffset(intensityMultiplier, scale, drawableHeight, viewHeight)

        translationMatrix.apply {
            set(imageMatrix)
            setScale(intensityMultiplier * scale, intensityMultiplier * scale)
            postTranslate(xOffset + xTranslation, yOffset + yTranslation)
        }.also {
            imageMatrix = it
        }
    }
}
