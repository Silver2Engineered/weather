package com.example.weather

import junit.framework.TestCase
import org.junit.Test

class CachedCityOverviewTest {
    @Test
    fun `City overview successfully converts to a domain model`() {
        val cachedCityOverview = testCachedCityOverview
        TestCase.assertEquals(cachedCityOverview.toDomainModel(), testCityOverview)
    }
}