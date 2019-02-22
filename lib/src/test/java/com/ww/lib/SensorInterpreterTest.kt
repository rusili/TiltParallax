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
package com.ww.lib

import com.ww.lib.parallax.domain.Event3
import com.ww.lib.parallax.domain.SensorInterpreter
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
