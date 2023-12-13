package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.StateDetails
import com.example.weather.repository.CityRepository
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    private lateinit var repository: CityRepository
    private val database = mockk<CityRoomDatabase>(relaxed=true)
    private lateinit var detailsViewModel: DetailsViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        repository = CityRepository(database)
    }

    @Test
    fun `Get city weather works successfully`() = runTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        detailsViewModel = DetailsViewModel.Factory(repository).create(DetailsViewModel::class.java)
        database.cityDetailsDao.insert(testCachedCityDetails)
        detailsViewModel.getCityWeather("500")
        Thread.sleep(5000)
        TestCase.assertEquals(detailsViewModel.cityData.value, StateDetails.Success(database.cityDetailsDao.getCityData(500).toDomainModel()))
    }

    }