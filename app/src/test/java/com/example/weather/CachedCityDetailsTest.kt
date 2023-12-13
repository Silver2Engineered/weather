package com.example.weather

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CachedCityDetailsTest {
    @Test
    fun `City details successfully converts to a domain model`() {
        val cachedCityDetails = testCachedCityDetails
        assertEquals(cachedCityDetails.toDomainModel(), testCityDetails)
    }
}