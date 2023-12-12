package com.example.weather

import junit.framework.TestCase
import org.junit.Test

class CityOverviewTest {
    @Test
    fun toCachedModel() {
        val cityOverview = testCityOverview
        TestCase.assertEquals(cityOverview.toCachedModel(), testCachedCityOverview)
    }
}