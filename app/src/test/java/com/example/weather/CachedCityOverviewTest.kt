package com.example.weather

import junit.framework.TestCase
import org.junit.Test

class CachedCityOverviewTest {
    @Test
    fun toDomainModelTest() {
        val cachedCityOverview = testCachedCityOverview
        TestCase.assertEquals(cachedCityOverview.toDomainModel(), testCityOverview)
    }
}