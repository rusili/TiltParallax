package com.rusili.lib

import com.rusili.lib.parallax.domain.Event3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Event3Test {

    @Test
    fun `Given valid event, When isValidEvent() is called, then Return true`() {
        // Given
        val testSubject = Event3(1f, 1f, 1f)

        // When
        val result = testSubject.isValid()

        // Then
        assertTrue(result)
    }

    @Test
    fun `Given invalid event, When isValidEvent() is called, then Return false`() {
        // Given
        val testSubject = Event3(0f, 1f, 1f)

        // When
        val result = testSubject.isValid()

        // Then
        assertFalse(result)
    }

    @Test
    fun `Given new valid values, When rotate() is called, then Return new values`() {
        // Given
        val testSubject = Event3(1f, 1f, 1f)

        // When
        testSubject.rotate(2f, 3f, 4f)

        // Then
        assertEquals(testSubject.x, 2f)
        assertEquals(testSubject.y, 3f)
        assertEquals(testSubject.z, 4f)
    }
}
