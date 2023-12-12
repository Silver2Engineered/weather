package com.example.weather

import junit.framework.TestCase
import org.junit.Test

class CityOverviewTest {
    @Test
    fun `City overview successfully converts to a cached model`() {
        val cityOverview = testCityOverview
        TestCase.assertEquals(cityOverview.toCachedModel(), testCachedCityOverview)
    }
}