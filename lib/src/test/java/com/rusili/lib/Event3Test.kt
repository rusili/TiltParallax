package com.rusili.lib

import com.rusili.lib.parallax.domain.Event3
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class Event3Test {

    @Test
    fun `Given valid event, When isValidEvent() is called, then Return true`() {
        // Given
        val testSubject = Event3(1f, 1f, 1f)

        // When
        val result = testSubject.isValidEvent()

        // Then
        result.shouldBeTrue()
    }

    @Test
    fun `Given invalid event, When isValidEvent() is called, then Return false`() {
        // Given
        val testSubject = Event3(0f, 1f, 1f)

        // When
        val result = testSubject.isValidEvent()

        // Then
        result.shouldBeFalse()
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
