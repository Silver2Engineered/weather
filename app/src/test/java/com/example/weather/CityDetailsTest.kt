package com.example.weather

import junit.framework.TestCase
import org.junit.Test

class CityDetailsTest {
    @Test
    fun toCachedModel() {
        val cityDetails = testCityDetails
        TestCase.assertEquals(cityDetails.toCachedModel(), testCachedCityDetails)
    }
}