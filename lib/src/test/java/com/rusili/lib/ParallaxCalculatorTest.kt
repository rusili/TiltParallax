package com.rusili.lib

import com.rusili.lib.parallax.domain.ParallaxCalculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val DEFAULT_TOLERANCE = 0.000001f

class ParallaxCalculatorTest {
    private val testSubject = ParallaxCalculator()

    @Test
    fun `Given maxTranslationChange is 0, When translate is called, Then return axis times scale`() {
        // Given
        val maxTranslationChange = 0f
        val translation = 1.2f
        val axis = 1.5f
        val scale = 2f

        // When
        val result = testSubject.translate(maxTranslationChange, translation, axis, scale)

        // Then
        assertEquals(result, 3f, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given a maxTranslationChange less than 0, When translate is called, Then return axis times scale`() {
        // Given
        val maxTranslationChange = -0.1f
        val translation = 1.2f
        val axis = 1f
        val scale = 0.5f

        // When
        val result = testSubject.translate(maxTranslationChange, translation, axis, scale)

        // Then
        assertEquals(result, 0.5f, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given a maxTranslationChange greater than 0, negative translation, and scale less than 1, When translate is called, Then return correct values`() {
        // Given
        val maxTranslationChange = 0.1f
        val translation = -1.2f
        val axis = 1f
        val scale = 0.5f

        // When
        val result = testSubject.translate(maxTranslationChange, translation, axis, scale)

        // Then
        assertEquals(result, -1.15f, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given a maxTranslationChange greater than 0, positive translation, and scale less than 1, When translate is called, Then return correct values`() {
        // Given
        val maxTranslationChange = 0.1f
        val translation = 1.2f
        val axis = 1f
        val scale = 0.5f

        // When
        val result = testSubject.translate(maxTranslationChange, translation, axis, scale)

        // Then
        assertEquals(result, 1.15f, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given a maxTranslationChange greater than 0, negative translation, and scale greater than 1, When translate is called, Then return correct values`() {
        // Given
        val maxTranslationChange = 0.1f
        val translation = -1.2f
        val axis = 1f
        val scale = 2.5f

        // When
        val result = testSubject.translate(maxTranslationChange, translation, axis, scale)

        // Then
        assertEquals(result, -0.95f, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given a maxTranslationChange greater than 0, positive translation, and scale greater than 1, When translate is called, Then return correct values`() {
        // Given
        val maxTranslationChange = 0.1f
        val translation = 1.2f
        val axis = 1f
        val scale = 2.5f

        // When
        val result = testSubject.translate(maxTranslationChange, translation, axis, scale)

        // Then
        assertEquals(result, 1.45f, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given drawableWidth times viewHeight is greater, When overallScale is called, Then return viewHeight divide by drawableHeight`() {
        // Given
        val drawableHeight = 400f
        val drawableWidth = 800f
        val viewHeight = 1080f
        val viewWidth = 1920f

        // When
        val result = testSubject.overallScale(drawableHeight, drawableWidth, viewHeight, viewWidth)

        // Then
        assertEquals(result, viewHeight / drawableHeight, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given drawableWidth times viewHeight is lesser, When overallScale is called, Then return viewWidth divide by drawableWidth`() {
        // Given
        val drawableHeight = 400f
        val drawableWidth = 200f
        val viewHeight = 1080f
        val viewWidth = 1920f

        // When
        val result = testSubject.overallScale(drawableHeight, drawableWidth, viewHeight, viewWidth)

        // Then
        assertEquals(result, 1920f / 200f, DEFAULT_TOLERANCE)
    }

    @Test
    fun `Given valid axis parameters, When axisOffset is called, Then return correct values`() {
        // Given
        val intensity = 1f
        val scale = 0.5f
        val drawableAxis = 400f
        val viewAxis = 1080f

        // Then
        val result = testSubject.axisOffset(intensity, scale, drawableAxis, viewAxis)

        // When
        assertEquals(result, 440f, DEFAULT_TOLERANCE)
    }
}
