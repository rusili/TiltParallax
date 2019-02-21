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
