package com.example.weather

import junit.framework.TestCase
import org.junit.Test

class CityDetailsTest {
    @Test
    fun `City details successfully converts to a cached model`() {
        val cityDetails = testCityDetails
        TestCase.assertEquals(cityDetails.toCachedModel(), testCachedCityDetails)
    }
}