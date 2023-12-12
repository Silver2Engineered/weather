package com.example.weather

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CachedCityDetailsTest {
    @Test
    fun toDomainModelTest() {
        val cachedCityDetails = testCachedCityDetails
        assertEquals(cachedCityDetails.toDomainModel(), testCityDetails)
    }
}