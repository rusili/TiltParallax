package com.rusili.lib

import com.rusili.lib.parallax.domain.Event3
import com.rusili.lib.parallax.domain.SensorInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

private const val DEFAULT_TOLERANCE = 0.000001f

class SensorInterpreterTest {
    private val testSubject = SensorInterpreter()

    @Test
    fun `Given invalid sensor event, When interpretSensorEvent is called, Then return null`() {
        // Given
        val eventArray = Event3(10f, 20f, 0f)

        // When
        val result = testSubject.interpretSensorEvent(eventArray, 0)

        // Then
        assertNull(result)
    }

    @Test
    fun `Given valid sensor event, When interpretSensorEvent is called, Then return adjusted values`() {
        // Given
        val eventArray = Event3(1f, 2f, 3f)

        // When
        val result = testSubject.interpretSensorEvent(eventArray, 0)

        // Then
        assertEquals(result?.x!!, 1f, DEFAULT_TOLERANCE)
        assertEquals(result.y, -0.64444447f, DEFAULT_TOLERANCE)
        assertEquals(result.z, -0.06666667f, DEFAULT_TOLERANCE)
    }
}
