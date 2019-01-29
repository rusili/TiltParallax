package com.rusili.lib

import com.rusili.lib.parallax.Event3
import com.rusili.lib.parallax.SensorInterpreter
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.Assert.assertEquals
import org.junit.Test

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
        result.shouldBeNull()
    }

    @Test
    fun `Given valid sensor event, When interpretSensorEvent is called, Then return adjusted values`() {
        // Given
        val eventArray = Event3(1f, 2f, 3f)

        // When
        val result = testSubject.interpretSensorEvent(eventArray, 0)

        // Then
        assertEquals(result?.x!!, 1f, DEFAULT_TOLERANCE)
        assertEquals(result.y, -0.24166667f, DEFAULT_TOLERANCE)
        assertEquals(result.z, -0.05f, DEFAULT_TOLERANCE)
    }
}
